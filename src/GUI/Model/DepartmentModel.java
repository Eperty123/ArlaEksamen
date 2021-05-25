package GUI.Model;

import BE.Department;
import BE.User;
import BLL.DepartmentManager;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {

    private DepartmentManager departmentManager = new DepartmentManager();
    private static DepartmentModel instance;

    public void exportPhoneNumbers(List<Department> departments) {
        departmentManager.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(Department department) {
        var departments = new ArrayList<Department>();
        departments.add(department);
        departmentManager.exportPhoneNumbers(departments);
    }

    public static DepartmentModel getInstance() {
        return instance == null ? instance = new DepartmentModel() : instance;
    }

    public void deleteDeparment(Department d) {
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

    public ObservableList<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }
}
