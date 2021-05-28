package BLL;

import BE.CSVUser;
import BE.Department;
import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.List;

public class UserManager {

    UserDAL userDAL = new UserDAL();

    /**
     * Gets a list of all users in the Database.
     *
     * @return a list of Users.
     */
    public List<User> getUsers() {
        return this.userDAL.getUsers();
    }

    /**
     * Adds a user to the Database
     *
     * @param user the desired user to be added.
     */
    public void addUser(User user, Department department) {
        userDAL.addUser(user, department);
    }

    /**
     * Import a list of CSVUsers in to the database.
     * @param users The list of CSVUsers to import.
     */
    public void addUsers(List<CSVUser> users) throws SQLException {
        userDAL.addUsers(users);
    }

    /**
     * Updates a User in the Database.
     *
     * @param user        the old user to be updated.
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

    public void updateUserDepartment(List<Department> departments){
        this.userDAL.updateUserDepartment(departments);
    }
}
