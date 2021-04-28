package BLL;

import BE.User;
import DAL.UserDAL;

import java.util.Collection;
import java.util.List;

public class UserManager {

    UserDAL userDAL = new UserDAL();



    public List<User> getUsers() {
        return userDAL.getUsers();
    }
}
