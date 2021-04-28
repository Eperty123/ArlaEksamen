package DAL;

import BE.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAL {
    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

    public List<User> getUsers() throws SQLException {
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

                allUsers.add(new User(id, firstName, lastName, userName, email, password, userRole));
            }
        }
        return allUsers;
    }

    public void deleteUser(){
        // TODO
    }

    public void addUser(){
        // TODO
    }

    public void updateUser(){
        // TODO
    }
}
