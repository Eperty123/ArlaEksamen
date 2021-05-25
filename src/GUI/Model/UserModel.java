package GUI.Model;

import BE.Department;
import BE.User;
import BE.UserType;
import BLL.UserManager;
import DAL.Parser.CSVParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     *
     * @param newUser object to be written to the database.
     */
    public void addUser(User newUser, Department department) {
        if (allUsers.stream().noneMatch(o -> o.getUserName().equals(newUser.getUserName()))) {
            userManager.addUser(newUser, department);
            updateUsers();
        }
    }

    /**
     * Retrieves a list of all users from the database (through UserManager).
     *
     * @return list of all users in the database.
     */
    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Updates a user in the database. Passes a old and new user object to UserManager,
     * which passes them on to UserDAL.
     *
     * @param oldUser object used to identify the row that is to be updated.
     * @param newUser object containing the updated User information.
     */
    public void updateUser(User oldUser, User newUser, Department oldDepartment, Department newDepartment) {
        userManager.updateUser(oldUser, newUser, oldDepartment, newDepartment);
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
     *
     * @param user user to be deleted.
     */
    public void deleteUser(User user) {
        userManager.deleteUser(user);
        updateUsers();
    }

    /**
     * Get the ScreenBit specified by an id.
     *
     * @param userId The id of the ScreenBit.
     * @return Returns the found ScreenBit.
     */
    public User getUser(int userId) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getId() == userId)
                return user;
        }
        return null;
    }

    /**
     * Get the User specified by a name.
     *
     * @param userName The name of the ScreenBit.
     * @return Returns the found ScreenBit.
     */
    public User getUser(String userName) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    /**
     * Get all users by the specified role.
     * @param role The role of the users to get.
     * @return Returns a List of Users filtered by the specified role.
     */
    public List<User> getAllUsersByRole(UserType role) {
        var filteredUsers = new ArrayList<User>();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserRole().equals(role))
                filteredUsers.add(user);
        }
        return filteredUsers;
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

    public void addUsersFromCSV(File csvFile){
        List<String[]> rows = CSVParser.parseFile(csvFile.getAbsolutePath()).getParsedData();
        List<User> users = new ArrayList<>();

    }
}
