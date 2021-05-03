package DAL;

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

    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM [User]");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()){
                int id = rs.getInt("Id");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String userName = rs.getString("UserName");
                String email = rs.getString("Email");
                int password = rs.getInt("Password");
                int userRole = rs.getInt("UserRole");

                allUsers.add(new User(id, firstName, lastName, userName, email, userRole, password));
            }
        } catch (SQLException throwables){
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
}
