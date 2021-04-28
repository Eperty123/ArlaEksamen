package BLL;

import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserManager {

    UserDAL userDAL = new UserDAL();

    public List<User> getUsers() throws SQLException {
        return userDAL.getUsers();
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
