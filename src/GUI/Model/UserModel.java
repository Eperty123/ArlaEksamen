package GUI.Model;

import BE.*;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class UserModel implements IUserCRUD {

    private static UserModel instance;
    private final ObservableList<User> allUsers = FXCollections.observableArrayList();
    private final UserManager userManager = new UserManager();

    public UserModel() {
        initialize();
    }

    private void initialize() {
        allUsers.addAll(userManager.getUsers());
    }

    @Override
    public List<User> getUsers() {
        return userManager.getUsers();
    }

    /**
     * If no user in allUsers have the username requested for the newUser,
     * the the newUser object is passed on to UserDal through UserManager for insertion.
     *
     * @param newUser object to be written to the database.
     */
    @Override
    public void addUser(User newUser, Department department) throws SQLException {

        userManager.addUser(newUser, department);

    }

    /**
     * Import a list of CSVUsers in to the database.
     *
     * @param users The list of CSVUsers to import.
     */
    @Override
    public void addUsers(List<CSVUser> users) throws SQLException {
        userManager.addUsers(users);
    }

    /**
     * Retrieves a list of all users from the database (through UserManager).
     *
     * @return list of all users in the database.
     */
    @Override
    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Updates a user in the database. Passes a old and new user object to UserManager,
     * which passes them on to UserDAL.
     *
     * @param user       object used to identify the row that is to be updated.
     * @param department object containing the updated User information.
     */
    @Override
    public void updateUser(User user, Department department) throws SQLException {
        userManager.updateUser(user, department);
    }

    /**
     * Passes a user object to UserManager for deleting. Then re-initializes allUsers with
     * the updateUsers() method.
     *
     * @param user user to be deleted.
     */
    @Override
    public void deleteUser(User user) throws SQLException {
        userManager.deleteUser(user);
    }

    @Override
    public void updateUserDepartment(List<Department> departments) throws SQLException {
        userManager.updateUserDepartment(departments);
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
     * @return the UserModel Singleton instance.
     */

    public static UserModel getInstance() {
        return instance == null ? instance = new UserModel() : instance;
    }


    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }
}
