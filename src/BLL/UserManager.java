package BLL;

import BE.Department;
import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserManager {

    UserDAL userDAL = new UserDAL();

    /**
     * Gets a list of all users in the Database.
     *
     * @return a list of Users.
     * @throws SQLException if the program cant access the Database.
     */
    public List<User> getUsers() throws SQLException {
        return userDAL.getUsers();
    }

    /**
     * Adds a user to the Database
     *
     * @param user the desired user to be added.
     */
    public void addUser(User user) {
        userDAL.addUser(user);
    }

    /**
     * Updates a User in the Database.
     *
     * @param user        the old user to be updated.
     * @param updatedUser the updated user.
     */
    public void updateUser(User user, User updatedUser) {
        userDAL.updateUser(user, updatedUser);
    }

    /**
     * Deletes a user in the Database.
     *
     * @param user the User to be deleted.
     */
    public void deleteUser(User user) {
        userDAL.deleteUser(user);
    }
}
