package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private static UserModel instance;
    private ObservableList<User> allUsers;

    private static UserManager userManager;

    public UserModel(){
        userManager = new UserManager();
        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(userManager.getUsers());

    }

    public static UserModel getInstance() { return instance == null ? instance = new UserModel() : instance;}

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

}
