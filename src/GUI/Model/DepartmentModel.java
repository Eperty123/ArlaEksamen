package GUI.Model;

import BE.Department;
import BE.User;
import BLL.DepartmentManager;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {

    private DepartmentManager departmentManager = new DepartmentManager();
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

    public void addDepartment(Department newDepartment) {
        departmentManager.addDepartment(newDepartment);
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        departmentManager.addSubDepartment(department, subDepartment);
    }

    public void deleteDepartment(Department d) {
        departmentManager.deleteDepartment(d);
    }

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

    public User getUser(String firstName, String lastName) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            var associatedUsers = department.getUsers();
            for (int u = 0; u < associatedUsers.size(); u++) {
                var associatedUser = associatedUsers.get(u);
                if (associatedUser.getFirstName().equals(firstName) && associatedUser.getLastName().equals(lastName))
                    return associatedUser;
            }
        }
        return null;
    }

    public User getUser(int userId) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            var associatedUsers = department.getUsers();
            for (int u = 0; u < associatedUsers.size(); u++) {
                var associatedUser = associatedUsers.get(u);
                if (associatedUser.getId() == userId)
                    return associatedUser;
            }
        }
        return null;
    }

    public Department getDepartment(int departmentId) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            if (department.getId() == departmentId)
                return department;
        }
        return null;
    }


    public Department getDepartment(String departmentName) {
        var departments = getAllDepartments();
        for (int i = 0; i < departments.size(); i++) {
            var department = departments.get(i);
            if (department.getName().equals(departmentName))
                return department;
        }
        return null;
    }

    public void updateDepartment(Department department) {
        departmentManager.editDepartment(department);
    }

    public ObservableList<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }

    public Department getSuperDepartment() {
        return departmentManager.getSuperDepartment();
    }
}
