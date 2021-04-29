package BLL;

import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;

public class LoginManager {


    private User currentUser;

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

    public User getCurrentUser() {
        return currentUser;
    }


}
