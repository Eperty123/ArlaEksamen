package GUI.Model;

import BE.Department;
import BE.User;
import BLL.DepartmentManager;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {

    private final DepartmentManager departmentManager = new DepartmentManager();
    private static DepartmentModel instance;

    public void exportPhoneNumbers(List<Department> departments) {
        departmentManager.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(List<Department> departments, String outputFile) {
        departmentManager.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(List<Department> departments, File outputFile) {
        departmentManager.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(Department department) {
        var departments = new ArrayList<Department>();
        departments.add(department);
        departmentManager.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(Department department, String outputFile) {
        var departments = new ArrayList<Department>();
        departments.add(department);
        departmentManager.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(Department department, File outputFile) {
        var departments = new ArrayList<Department>();
        departments.add(department);
        departmentManager.exportPhoneNumbers(departments, outputFile);
    }

    public static DepartmentModel getInstance() {
        return instance == null ? instance = new DepartmentModel() : instance;
    }

    /**
     * Adds a department to the database.
     * @param newDepartment
     */
    public void addDepartment(Department newDepartment) {
        departmentManager.addDepartment(newDepartment);
    }

    /**
     * Adds a sub department to a department, and creates a relation in the database.
     * @param department
     * @param subDepartment
     */
    public void addSubDepartment(Department department, Department subDepartment) {
        departmentManager.addSubDepartment(department, subDepartment);
    }

    /**
     * Deletes a department from the database.
     * @param d
     * @throws SQLException
     */
    public void deleteDepartment(Department d) throws SQLException {
        departmentManager.deleteDepartment(d);
    }

    // TODO migrate to department class
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
     * @param departmentId
     * @return
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

    /**
     * Updates a department in the database.
     * @param department
     */
    public void updateDepartment(Department department) {
        departmentManager.editDepartment(department);
    }

    /**
     * Returns an observable list of all departments.
     * @return
     */
    public ObservableList<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }

    /**
     * Returns a department's super department.
     * @return
     */
    public List<Department> getSuperDepartment() {
        return departmentManager.getSuperDepartment();
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
     * @return
     */
    public List<Department> getSuperDepartments() {
        return departmentManager.getSuperDepartment();
    }
}
