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

    public void addUser(User user){
        userDAL.addUser(user);
    }

    public void updateUser(User user, User updatedUser){
        userDAL.updateUser(user, updatedUser);
    }

    public void deleteUser(User user) {
        userDAL.deleteUser(user);
    }
}
