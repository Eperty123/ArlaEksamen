package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class UserModel {

    private static UserModel instance;
    private ObservableList<User> allUsers;
    private UserManager userManager;

    public UserModel() {
        userManager = new UserManager();
        allUsers = FXCollections.observableArrayList();
        try {
            allUsers.addAll(userManager.getUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * @return the UserModel Singleton instance.
     */
    public static UserModel getInstance() {
        return instance == null ? instance = new UserModel() : instance;
    }

    /**
     * If no user in allUsers have the username requested for the newUser,
     * the the newUser object is passed on to UserDal through UserManager for insertion.
     * @param newUser object to be written to the database.
     */
    public void addUser(User newUser) {
        if(allUsers.stream().noneMatch(o -> o.getUserName().equals(newUser.getUserName()))){
            userManager.addUser(newUser);
            updateUsers();
        }
    }

    /**
     * Retrieves a list of all users from the database (through UserManager).
     * @return list of all users in the database.
     */
    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Updates a user in the database. Passes a old and new user object to UserManager,
     * which passes them on to UserDAL.
     * @param oldUser object used to identify the row that is to be updated.
     * @param newUser object containing the updated User information.
     */
    public void updateUser(User oldUser, User newUser) {
        userManager.updateUser(oldUser, newUser);
        updateUsers();
    }

    /**
     * Method used to re-initialize the allUsers list.
     */
    public void updateUsers() {
        allUsers.clear();
        try {
            allUsers.addAll(userManager.getUsers());
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Passes a user object to UserManager for deleting. Then re-initializes allUsers with
     * the updateUsers() method.
     * @param user user to be deleted.
     */
    public void deleteUser(User user){
        userManager.deleteUser(user);
        updateUsers();
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
            System.out.println(String.format("%s singleton was reset.", getClass().getSimpleName()));
        }
    }
}
