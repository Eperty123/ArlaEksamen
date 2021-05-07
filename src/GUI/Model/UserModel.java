package GUI.Model;

import BE.ScreenBit;
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

    public static UserModel getInstance() {
        return instance == null ? instance = new UserModel() : instance;
    }

    public void addUser(User newUser) {
        userManager.addUser(newUser);
        updateUsers();
    }

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public void assignScreenByUserName(ScreenBit screenBit, String userName){
        for (User u : allUsers){
            if(u.getUserName().equals(userName)){
                u.setAssignedScreen(screenBit);
            }
        }
    }

    public void updateUser(User oldUser, User newUser) {
        userManager.updateUser(oldUser, newUser);
        allUsers.remove(oldUser);
        allUsers.add(newUser);
    }

    public void updateUsers() {
        allUsers.clear();
        try {
            allUsers.addAll(userManager.getUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(User user){
        ScreenModel.getInstance().removeScreenRights(user, user.getAssignedScreen());

        allUsers.remove(user);
        userManager.deleteUser(user.getId());
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
