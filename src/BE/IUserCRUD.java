package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IUserCRUD {

    /**
     * Gets a list of all users in the Database.
     *
     * @return a list of Users.
     */
    List<User> getUsers();

    /**
     * Gets an ObservableList of all users in the Database.
     *
     * @return Returns an ObservableList of Users.
     */
    ObservableList<User> getAllUsers();

    /**
     * Adds a user to the Database
     *
     * @param user the desired user to be added.
     */
    void addUser(User user, Department department) throws SQLException;

    /**
     * Import a list of CSVUsers in to the database.
     *
     * @param users The list of CSVUsers to import.
     */
    void addUsers(List<CSVUser> users) throws SQLException;

    /**
     * Updates a User in the Database.
     *
     * @param user       the old user to be updated.
     * @param department the updated user.
     */
    void updateUser(User user, Department department) throws SQLException;

    /**
     * Deletes a user in the Database.
     *
     * @param user the User to be deleted.
     */
    void deleteUser(User user) throws SQLException;

    /**
     * Update the user's departments.
     *
     * @param departments The departments to update.
     * @throws SQLException
     */
    void updateUserDepartment(List<Department> departments) throws SQLException;

    /**
     * Does any users exist at all in the database?
     *
     * @return Returns true if yes otherwise false.
     */
    boolean hasUsersLoaded();
}
