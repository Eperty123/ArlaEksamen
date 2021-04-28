package BLL;

import BE.User;
import DAL.UserDAL;

public class LoginManager {

    private static User currentUser;

    public void attemptLogin(String username, String password) {
        UserDAL userDAL = new UserDAL();
        for (User u : userDAL.getUsers()) {
            if (username.equalsIgnoreCase(u.getUserName()) && password.equals(u.getPassword())) {
                currentUser=u;
            }
        }
    }

}
