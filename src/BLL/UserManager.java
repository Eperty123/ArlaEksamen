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
    public int addUser(User user, Department department) {
        return this.userDAL.addUser(user, department);
    }

    /**
     * Import a list of CSVUsers in to the database.
     * @param users The list of CSVUsers to import.
     */
    public void addUsers(List<CSVUser> users) {
        this.userDAL.addUsers(users);
    }

    /**
     * Updates a User in the Database.
     *
     * @param user        the old user to be updated.
     * @param updatedUser the updated user.
     */
    public void updateUser(User user, User updatedUser, Department oldDepartment, Department newDepartment) {
        this.userDAL.updateUser(user, updatedUser, oldDepartment, newDepartment);
    }

    /**
     * Deletes a user in the Database.
     *
     * @param user the User to be deleted.
     */
    public void deleteUser(User user) {
        this.userDAL.deleteUser(user);
    }

    public void updateUserDepartment(List<Department> departments){
        this.userDAL.updateUserDepartment(departments);
    }
}
