package BLL;

import BE.User;
import DAL.UserDAL;
import GUI.Model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class LoginManager {


    private static User currentUser;

    /**
     * Attempt a login with the given username and password.
     * @param username the username given by the user.
     * @param password the password given by the user.
     * @return true if the login was successful.
     */
    public boolean attemptLogin(String username, String password) {
        PasswordManager passwordManager = new PasswordManager();
        List<User> allUsers = null;
        allUsers = UserModel.getInstance().getAllUsers();

        for (User u : allUsers) {
            if (username.equalsIgnoreCase(u.getUserName()) && passwordManager.encrypt(password) == (u.getPassword())) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the currently logged in user.
     * @return the currently logged in user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged in user to a user.
     * Typically used when logging out, to set the current user to null.
     * @param u the desired user.
     */
    public static void setCurrentUser(User u) {
        currentUser = u;
    }


}
