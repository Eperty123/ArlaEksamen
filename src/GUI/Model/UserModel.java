package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class UserModel {

    private static UserModel instance;
    private ObservableList<User> allUsers;

    private static UserManager userManager;

    public UserModel()  {
        userManager = new UserManager();
        allUsers = FXCollections.observableArrayList();
        try {
            allUsers.addAll(userManager.getUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static UserModel getInstance() throws SQLException { return instance == null ? instance = new UserModel() : instance;}

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

}
