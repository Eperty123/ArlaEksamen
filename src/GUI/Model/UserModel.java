package GUI.Model;

import BE.CSVUser;
import BE.Department;
import BE.User;
import BE.UserType;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public final class UserModel {

    private static UserModel instance;
    private final ObservableList<User> allUsers;
    private final UserManager userManager;

    private UserModel() {
        userManager = new UserManager();
        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(userManager.getUsers());

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
        }
    }

    /**
     * Import a list of CSVUsers in to the database.
     * @param users The list of CSVUsers to import.
     */
    public void addUsers(List<CSVUser> users) {
        userManager.addUsers(users);
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
    }

    /**
     * Method used to re-initialize the allUsers list.
     */
    public void updateUsers() {
        allUsers.clear();
        allUsers.addAll(userManager.getUsers());

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
     * Get the User specified by an id.
     *
     * @param userId The id of the User.
     * @return Returns the found User.
     */
    public User getUserById(int userId) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getId() == userId)
                return user;
        }
        return null;
    }

    /**
     * Get the User specified by username.
     *
     * @param userName The username to get.
     * @return Returns the found User.
     */
    public User getUserByUsername(String userName) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    /**
     * Get the User specified by email.
     *
     * @param email The email to get.
     * @return Returns the found User.
     */
    public User getUserByEmail(String email) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    /**
     * Get the User specified by a first and last name.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @return Returns the found User.
     */
    public User getUserByFirstLastName(String firstName, String lastName) {
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

    /**
     * Get all users by the specified role.
     *
     * @param role The role of the users to get.
     * @return Returns a List of Users filtered by the specified role.
     */
    public List<User> getAllUsersByRole(UserType role) {
        var filteredUsers = new ArrayList<User>();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUserRole() == role)
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
        }
    }

    public void updateUserDepartment(List<Department> departments) {
        userManager.updateUserDepartment(departments);
    }
}
