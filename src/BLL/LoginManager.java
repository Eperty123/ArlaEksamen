package BLL;

import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;

public class LoginManager {

    private static User currentUser;

    public void attemptLogin(String username, String password) throws SQLException {
        UserDAL userDAL = new UserDAL();
        for (User u : userDAL.getUsers()) {
            if (username.equalsIgnoreCase(u.getUserName()) && password.equals(u.getPassword())) {
                currentUser=u;
            }
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
