package BLL;

import BE.CSVUser;
import BE.Department;
import BE.IUserCRUD;
import BE.User;
import DAL.UserDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class UserManager implements IUserCRUD {

    UserDAL userDAL = new UserDAL();
    private ObservableList<User> allUsers;
    private boolean usersLoaded;

    public UserManager() {
        initialize();
    }

    private void initialize() {
        var users = userDAL.getUsers();
        if (users != null) {
            allUsers = FXCollections.observableArrayList();
            allUsers.addAll(users);
            usersLoaded = true;
        }
    }

    /**
     * Gets a list of all users in the Database.
     *
     * @return a list of Users.
     */
    public List<User> getUsers() {
        return userDAL.getUsers();
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Adds a user to the Database
     *
     * @param user the desired user to be added.
     */
    public void addUser(User user, Department department) throws SQLException {
        userDAL.addUser(user, department);
    }

    /**
     * Import a list of CSVUsers in to the database.
     *
     * @param users The list of CSVUsers to import.
     */
    public void addUsers(List<CSVUser> users) throws SQLException {
        userDAL.addUsers(users);
    }

    /**
     * Updates a User in the Database.
     *
     * @param user       the old user to be updated.
     * @param department the updated user.
     */
    public void updateUser(User user, Department department) throws SQLException {
        userDAL.updateUser(user, department);
    }

    /**
     * Deletes a user in the Database.
     *
     * @param user the User to be deleted.
     */
    public void deleteUser(User user) throws SQLException {
        userDAL.deleteUser(user);
    }

    /**
     * Updates the user-department relations in the database, based on a list of departments (which contains
     * the assigned users).
     * @param departments
     */
    public void updateUserDepartment(List<Department> departments) throws SQLException {
        userDAL.updateUserDepartment(departments);
    }

    @Override
    public boolean hasUsersLoaded() {
        return usersLoaded;
    }
}
