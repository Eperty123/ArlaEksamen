package DAL;

import BE.ScreenBit;
import BE.User;
import BE.UserType;
import DAL.DbConnector.DbConnectionHandler;

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
     * Creates a list of all users in the database. The
     * @return
     */
    public List<User> getUsers(){
        List<User> allUsers = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT\n" +
                    "  [User].Id AS UserId,\n" +
                    "  [User].FirstName,\n" +
                    "  [User].LastName,\n" +
                    "  [User].UserName,\n" +
                    "  [User].Email,\n" +
                    "  [User].Password,\n" +
                    "  [User].UserRole,\n" +
                    "  Screen.Id AS ScreenId,\n" +
                    "  Screen.ScreenName,\n" +
                    "  Screen.ScreenInfo\n" +
                    "FROM [User]\n" +
                    "JOIN ScreenRights\n" +
                    "  ON ScreenId = ScreenRights.ScreenId\n" +
                    "LEFT JOIN Screen \n" +
                    "  ON [User].UserName = ScreenRights.UserName;   ");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()) {
                User newUser = resultSetParser.getUser(rs);
                ScreenBit screenBit = resultSetParser.getScreenBit(rs);
                addUsersAndScreenBits(allUsers, newUser, screenBit);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }

    public void deleteUser(int userId) {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("DELETE FROM [User] WHERE Id = ?");
            pSql.setInt(1, userId);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(String username) {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("DELETE FROM [User] WHERE UserName = ?");
            pSql.setString(1, username);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addUser(User user){

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO [User] VALUES(?,?,?,?,?,?)");
            pSql.setString(1, user.getFirstName());
            pSql.setString(2, user.getLastName());
            pSql.setString(3, user.getUserName());
            pSql.setString(4, user.getEmail());
            pSql.setInt(5, user.getPassword());
            pSql.setInt(6, user.getUserRole() == UserType.Admin ? 0 : 1);
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void updateUser(User user, User updatedUser){

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("UPDATE [User] SET FirstName = ?, LastName = ?, UserName = ?, Email = ?, Password = ?, UserRole = ? WHERE Id = ?");
            pSql.setString(1, updatedUser.getFirstName());
            pSql.setString(2, updatedUser.getLastName());
            pSql.setString(3, updatedUser.getUserName());
            pSql.setString(4, updatedUser.getEmail());
            pSql.setInt(5, updatedUser.getPassword());
            pSql.setInt(6, updatedUser.getUserRole() == UserType.Admin ? 0 : 1);
            pSql.setInt(7, user.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private ScreenBit getScreenBit(ResultSet rs) throws SQLException {

        int screenId = rs.getInt("ScreenId");
        String name = rs.getString("ScreenName");
        String screenInfo = rs.getString("ScreenInfo");
        ScreenBit newScreenBit = new ScreenBit(screenId, name, screenInfo);

        return newScreenBit;
    }

    private User getUser(ResultSet rs) throws SQLException {

        int userId = rs.getInt("UserId");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String userName = rs.getString("UserName");
        String email = rs.getString("Email");
        int password = rs.getInt("Password");
        int userRole = rs.getInt("UserRole");
        User assignedUser = new User(userId, firstName, lastName,  userName, email, userRole, password);

        return assignedUser;
    }

    private void addUsersAndScreenBits(List<User> allUsers, User newUser, ScreenBit screenBit) {
        if(allUsers.stream().noneMatch(o -> o.getId() == newUser.getId())){
            newUser.setAssignedScreen(screenBit);
            allUsers.add(newUser);
        } else{
            for(User u : allUsers){
                if(u.getId() == newUser.getId()){
                    u.setAssignedScreen(screenBit);
                }
            }
        }
    }

}
