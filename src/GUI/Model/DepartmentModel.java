package GUI.Model;

import BE.Department;
import BE.IDepartmentCRUD;
import BE.User;
import BLL.DepartmentManager;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentModel implements IDepartmentCRUD {

    private final DepartmentManager departmentManager = new DepartmentManager();
    private static DepartmentModel instance;

    public static DepartmentModel getInstance() {
        return instance == null ? instance = new DepartmentModel() : instance;
    }

    /**
     * Add a new department to the database.
     *
     * @param newDepartment The department to add.
     * @throws SQLException
     */
    public void addDepartment(Department newDepartment) throws SQLException {
        departmentManager.addDepartment(newDepartment);
    }

    /**
     * Remove the given department from the database.
     *
     * @param department The department to remove.
     */
    @Override
    public void removeDepartment(Department department) {
        departmentManager.removeDepartment(department);
    }

    /**
     * Edit the given department.
     *
     * @param department The department to edit.
     * @throws SQLException
     */
    @Override
    public void editDepartment(Department department) throws SQLException {
        departmentManager.editDepartment(department);
    }

    /**
     * Add a sub department to the given main department.
     *
     * @param department    The main department to add the sub department to.
     * @param subDepartment The sub department to add to the main department.
     * @throws SQLException
     */
    public void addSubDepartment(Department department, Department subDepartment) throws SQLException {
        departmentManager.addSubDepartment(department, subDepartment);
    }

    /**
     * Remove the specified department from the database.
     *
     * @param d The department to remove.
     * @throws SQLException
     */
    public void deleteDepartment(Department d) throws SQLException {
        departmentManager.deleteDepartment(d);
    }

    /**
     * Get the given user filtered by its username.
     *
     * @param username The username of the user to get.
     * @return Returns the found user.
     */
    public User getUser(String username) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            var associatedUsers = department.getUsers();
            for (int u = 0; u < associatedUsers.size(); u++) {
                var associatedUser = associatedUsers.get(u);
                if (associatedUser.getUserName().equals(username))
                    return associatedUser;
            }
        }
        return null;
    }


    /**
     * Retrieves a department matching the specified department id.
     *
     * @param departmentId The department id.
     * @return Returns the desired department.
     */
    public Department getDepartment(int departmentId) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            if (department.getId() == departmentId)
                return department;
        }
        return null;
    }

    public void updateDepartment(Department department) throws SQLException {
        departmentManager.editDepartment(department);
    }

    /**
     * Returns an observable list of all departments.
     *
     * @return Returns an ObservableList of departments.
     */
    public ObservableList<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }

    /**
     * Returns a list of the departments(') super departments.
     *
     * @return Returns a list of departments(')?
     */
    public List<Department> getSuperDepartments() {
        return departmentManager.getSuperDepartments();
    }
}
