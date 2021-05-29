package DAL;

import BE.CSVUser;
import BE.Department;
import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAL {
    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();


    /**
     * Creates a list of all users in the database. The query join the User and Screen tables through
     * the ScreenRights junction table. Users who
     *
     * @return
     */
    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT " +
                            "[User].*, " +
                            "Screen.Id AS ScreenId, " +
                            "Screen.ScreenName, " +
                            "Screen.ScreenInfo " +
                            "FROM [User] " +
                            "LEFT OUTER JOIN ScreenRights " +
                            "ON [User].UserName = ScreenRights.UserName " +
                            "LEFT OUTER JOIN Screen " +
                            "ON Screen.Id = ScreenRights.ScreenId AND ScreenRights.UserName = [User].UserName");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {

                User newUser = resultSetParser.getUser(rs);
                ScreenBit screenBit = resultSetParser.getScreenBit(rs);
                addUsersAndScreenBits(allUsers, newUser, screenBit);
            }

            return allUsers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //throw throwables;
            return null;
        }
    }


    /**
     * Method performs an INSERT query to create a new user/row in the User table.
     *
     * @param user object containing information on the new user.
     */
    public void addUser(User user, Department department) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO [User] VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            setUserValues(user, pSql);
            pSql.executeUpdate();
            user.setId(resultSetParser.getGeneratedKey(pSql));

            if (department != null) {
                addUserDepartmentRelation(con, user, department);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    private void setUserValues(User user, PreparedStatement pSql) throws SQLException {
        pSql.setString(1, user.getFirstName());
        pSql.setString(2, user.getLastName());
        pSql.setString(3, user.getUserName());
        pSql.setString(4, user.getEmail());
        pSql.setInt(5, user.getPassword());
        pSql.setInt(6, user.getUserRole().ordinal());
        pSql.setInt(7, user.getPhone());
        pSql.setInt(8, user.getGender().ordinal());
        pSql.setString(9, user.getPhotoPath());
        pSql.setString(10, user.getTitle());
    }


    /**
     * Import a list of CSVUsers in to the database.
     *
     * @param users The list of CSVUsers to import.
     */
    public void addUsers(List<CSVUser> users) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            PreparedStatement pSql = con.prepareStatement("INSERT INTO [User] VALUES(?,?,?,?,?,?,?,?,?,?)");

            for (CSVUser user : users) {
                setUserValues(user, pSql);
                pSql.addBatch();

                if (user.getDepartment() != null) {
                    addUserDepartmentRelation(con, user, user.getDepartment());
                }
            }
            try {
                pSql.executeBatch();
            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Associate a relation to a department for the specfied user.
     *
     * @param con        The database connection to use.
     * @param user       The user to associate with.
     * @param department The department to associate the user with.
     * @throws SQLException
     */
    private void addUserDepartmentRelation(Connection con, User user, Department department) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("INSERT INTO DepartmentUser VALUES(?,?)");
        pSql.setInt(1, department.getId());
        pSql.setString(2, user.getUserName());
        pSql.execute();

    }

    private void addUserDepartmentRelation(Connection con, User user, int departmentId) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("INSERT INTO DepartmentUser VALUES(?,?)");
        pSql.setInt(1, departmentId);
        pSql.setString(2, user.getUserName());
        pSql.execute();

    }

    /**
     * Updates an existing user in the database's User table.
     *
     * @param user       object used to identify the row to be updated.
     * @param department object containing the new department information.
     */
    public void updateUser(User user, Department department) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            PreparedStatement pSql = con.prepareStatement("UPDATE [User] SET FirstName = ?, LastName = ?, " +
                    "UserName = ?, Email = ?, Password = ?, UserRole = ?, Phone = ?, Gender = ?, " +
                    "PhotoPath = ?, Title = ? WHERE Id = ?");
            setUserValues(user, pSql);
            pSql.setInt(11, user.getId());

            try {
                pSql.execute();
                updateDepartmentUser(con, user, department);
            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;

        }
    }


    public void updateUserDepartment(List<Department> departments) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            deleteAllUserDepartmentAssociation(con);
            PreparedStatement pSql = con.prepareStatement("INSERT INTO DepartmentUser VALUES(?,?)");
            for (Department d : departments) {
                for (User u : d.getUsers()) {
                    pSql.setInt(1, d.getId());
                    pSql.setString(2, u.getUserName());
                    pSql.addBatch();
                }
            }
            pSql.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

    private void deleteAllUserDepartmentAssociation(Connection con) throws SQLException {
        PreparedStatement pSql = con.prepareStatement("DELETE FROM DepartmentUser");
        pSql.execute();
    }

    private void updateDepartmentUser(Connection con, User user, Department department) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("UPDATE DepartmentUser SET DepartmentId=?, UserName=? WHERE UserName=? AND DepartmentId=?");
        pSql.setInt(1, department.getId());
        pSql.setString(2, user.getUserName());
        pSql.setString(3, user.getUserName());
        pSql.setInt(4, department.getId());
        pSql.execute();
    }


    /**
     * Deletes a user from the User table in the database (referencing Id).
     *
     * @param user object used to identify which row to delete in database.
     */
    public void deleteUser(User user) throws SQLException {
        // Deletes all User-Screen associations in the ScreenRights junction table.


        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            deleteUserScreenAssociation(con, user);
            deleteUserDepartmentAssociation(con, user);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM [User] WHERE Id=?");
            pSql.setInt(1, user.getId());

            try {
                pSql.execute();
            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

    private void deleteUserDepartmentAssociation(Connection con, User user) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("DELETE FROM DepartmentUser WHERE UserName=?");
        pSql.setString(1, user.getUserName());
        pSql.execute();
    }

    /**
     * This helper method updates the allUsers list with data retrieved from the ResultSet in the getUsers() method.
     * <p>
     * - If a user does not exist in allUsers, first the ScreenBit is assigned to the user,
     * and then the user is added to allUsers.
     * - If a user does exist in allUsers, the ScreenBit is added to the users list of assigned ScreenBits.
     *
     * @param allUsers
     * @param newUser   object created from a ResultSet row
     * @param screenBit object created from ResultSet row
     */
    private void addUsersAndScreenBits(List<User> allUsers, User newUser, ScreenBit screenBit) {
        if (allUsers.stream().noneMatch(o -> o.getId() == newUser.getId())) {

            if (screenBit.getName() != null) {
                newUser.getAssignedScreenBits().add(screenBit);
            }
            allUsers.add(newUser);
        } else {

            for (User u : allUsers) {
                if (u.getId() == newUser.getId() && screenBit.getName() != null) {
                    u.getAssignedScreenBits().add(screenBit);
                }
            }
        }
    }

    /**
     * Deletes all rows in ScreenRights table associated with the user. This has to be done before
     * the user can be deleted due to foreign key constraints in the ScreenRights table.
     *
     * @param user used to identify which rows to delete.
     */
    private void deleteUserScreenAssociation(Connection con, User user) throws SQLException {

        try (PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=?")) {

            pSql.setString(1, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

}
