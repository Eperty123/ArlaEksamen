package BLL;

import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.List;

public class LoginManager {


    private static User currentUser;

    public boolean attemptLogin(String username, String password) throws SQLException {
        PasswordManager passwordManager = new PasswordManager();
        UserDAL userDAL = new UserDAL();
        for (User u : userDAL.getUsers()) {
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
