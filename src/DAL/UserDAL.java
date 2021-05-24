package DAL;

import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAL {
    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private ResultSetParser resultSetParser = new ResultSetParser();


    /**
     * Creates a list of all users in the database. The query join the User and Screen tables through
     * the ScreenRights junction table. Users who
     * @return
     */
    public List<User> getUsers(){
        List<User> allUsers = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT " +
                            "[User].*, " +
                            "Screen.Id AS ScreenId, " +
                            "Screen.ScreenName, " +
                            "Screen.ScreenInfo, " +
                            "DepartmentUser.DepartmentId " +
                            "FROM [User] " +
                            "LEFT OUTER JOIN ScreenRights " +
                            "ON [User].UserName = ScreenRights.UserName " +
                            "LEFT OUTER JOIN Screen " +
                            "ON Screen.Id = ScreenRights.ScreenId AND ScreenRights.UserName = [User].UserName " +
                            "LEFT OUTER JOIN DepartmentUser ON DepartmentUser.UserName = [User].UserName;");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while(rs.next()) {

                User newUser = resultSetParser.getUser(rs);
                ScreenBit screenBit = resultSetParser.getScreenBit(rs);
                addUsersAndScreenBits(allUsers, newUser, screenBit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all users " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return allUsers;
    }




    /**
     * Method performs an INSERT query to create a new user/row in the User table.
     * @param user object containing information on the new user.
     */
    public void addUser(User user){

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO [User] VALUES(?,?,?,?,?,?,?,?,?,?)");
            pSql.setString(1, user.getFirstName());
            pSql.setString(2, user.getLastName());
            pSql.setString(3, user.getUserName());
            pSql.setString(4, user.getEmail());
            pSql.setInt(5, user.getPassword());
            pSql.setInt(6, user.getUserRole().ordinal());
            pSql.setInt(7,user.getPhone());
            pSql.setInt(8,user.getGender().ordinal());
            pSql.setString(9, user.getPhotoPath());
            pSql.setString(10, user.getTitle());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a user " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Updates an existing user in the database's User table.
     * @param user object used to identify the row to be updated.
     * @param updatedUser object containing the new user information.
     */
    public void updateUser(User user, User updatedUser){

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("UPDATE [User] SET FirstName = ?, LastName = ?, " +
                    "UserName = ?, Email = ?, Password = ?, UserRole = ?, Phone = ?, Gender = ?, " +
                    "PhotoPath = ?, Title = ? WHERE Id = ?");
            pSql.setString(1, updatedUser.getFirstName());
            pSql.setString(2, updatedUser.getLastName());
            pSql.setString(3, updatedUser.getUserName());
            pSql.setString(4, updatedUser.getEmail());
            pSql.setInt(5, updatedUser.getPassword());
            pSql.setInt(6, updatedUser.getUserRole().ordinal());
            pSql.setInt(7,updatedUser.getPhone());
            pSql.setInt(8,updatedUser.getGender().ordinal());
            pSql.setString(9, updatedUser.getPhotoPath());
            pSql.setString(10, updatedUser.getTitle());;
            pSql.setInt(11, user.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a user " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }


    /**
     * Deletes a user from the User table in the database (referencing Id).
     * @param user object used to identify which row to delete in database.
     */
    public void deleteUser(User user) {
        // Deletes all User-Screen associations in the ScreenRights junction table.
        deleteUserScreenAssociation(user);

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("DELETE FROM [User] WHERE Id=?");
            pSql.setInt(1, user.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a user " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }

    /**
     * This helper method updates the allUsers list with data retrieved from the ResultSet in the getUsers() method.
     *
     * - If a user does not exist in allUsers, first the ScreenBit is assigned to the user,
     * and then the user is added to allUsers.
     * - If a user does exist in allUsers, the ScreenBit is added to the users list of assigned ScreenBits.
     * @param allUsers
     * @param newUser object created from a ResultSet row
     * @param screenBit object created from ResultSet row
     */
    private void addUsersAndScreenBits(List<User> allUsers, User newUser, ScreenBit screenBit) {
        if(allUsers.stream().noneMatch(o -> o.getId() == newUser.getId())){

            if(screenBit.getName() != null){
                newUser.getAssignedScreenBits().add(screenBit);
            }
            allUsers.add(newUser);
        } else{

            for(User u : allUsers){
                if(u.getId() == newUser.getId() && screenBit.getName() != null){
                    u.getAssignedScreenBits().add(screenBit);
                }
            }
        }
    }

    /**
     * Deletes all rows in ScreenRights table associated with the user. This has to be done before
     * the user can be deleted due to foreign key constraints in the ScreenRights table.
     * @param user used to identify which rows to delete.
     */
    private void deleteUserScreenAssociation(User user){

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=?");
            pSql.setString(1, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a user - screen association " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }

}
