package BLL;

import BE.Department;
import BE.User;
import DAL.DepartmentDAL;
import GUI.Model.DataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class DepartmentManager {
    private final ObservableList<Department> departmentList = FXCollections.observableArrayList();
    private final DepartmentDAL departmentDAL = new DepartmentDAL();

    public DepartmentManager() {
        departmentList.setAll(departmentDAL.getDepartments());
    }

    /**
     * Returns a department's super department.
     * @return
     */
    public List<Department> getSuperDepartment() {
        ObservableList<Department> tmp = FXCollections.observableArrayList(DataModel.getInstance().getDepartments());
        DataModel.getInstance().getDepartments().forEach(d -> {
            d.getSubDepartments().forEach(sd -> tmp.remove(sd));
        });
        if(DataModel.getInstance().getDepartments().isEmpty())
        {
            Department newDepartment = new Department("new Department1");
            User user = new User();
            user.setUserName("place");
            newDepartment.setManager(user);
            addDepartment(newDepartment);
            tmp.add(newDepartment);
        }
        return tmp;
    }

    /**
     * Returns an observable list of all departments in the database (with assigned users and sub departments).
     * @return
     */
    public ObservableList<Department> getAllDepartments() {
        return departmentList;
    }

    /**
     * Adds a department to the database.
     * @param department
     */
    public void addDepartment(Department department) {
        departmentDAL.addDepartment(department);
    }

    /**
     * Removes a department from the database, referencing it's id.
     * @param department
     */
    public void removeDepartment(Department department) {
        departmentList.remove(department);
    }

    /**
     * Edits a department in the database, referencing it's id.
     * @param department
     */
    public void editDepartment(Department department) {
        departmentDAL.updateDepartment(department);
    }

    public void exportPhoneNumbers(List<Department> departments) {
        departmentDAL.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(List<Department> departments, String outputFile) {
        departmentDAL.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(List<Department> departments, File outputFile) {
        departmentDAL.exportPhoneNumbers(departments, outputFile);
    }

    /**
     * Deletes a department from the database.
     * @param d
     * @throws SQLException
     */
    public void deleteDepartment(Department d) throws SQLException {
        departmentDAL. deleteDepartment(d);
    }

    /**
     * Adds a sub department to a department in the database.
     * @param department
     * @param subDepartment
     */
    public void addSubDepartment(Department department, Department subDepartment) {
        departmentDAL.addSubDepartment(department, subDepartment);
    }
}
