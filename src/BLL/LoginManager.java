package BLL;

import BE.User;
import DAL.UserDAL;
import GUI.Model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class LoginManager {


    private static User currentUser;

    public boolean attemptLogin(String username, String password)  {

        PasswordManager passwordManager = new PasswordManager();
        List<User> allUsers = null;
        allUsers = UserModel.getInstance().getAllUsers();

        for (User u : allUsers) {
            if (username.equals(u.getUserName()) && passwordManager.encrypt(password) == (u.getPassword())) {
                currentUser=u;
                return true;
            }
        }
        return false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }


}
