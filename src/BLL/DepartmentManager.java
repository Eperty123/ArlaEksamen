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
     * Gets the departments that are not subDepartments, if there are no departments in the database it returns a new Department
     *
     * @return A list of super departments
     */
    public List<Department> getSuperDepartment() {
        ObservableList<Department> tmp = FXCollections.observableArrayList(DataModel.getInstance().getDepartments());
        DataModel.getInstance().getDepartments().forEach(department -> {
            department.getSubDepartments().forEach(subDepartment -> tmp.remove(subDepartment));
        });
        if (DataModel.getInstance().getDepartments().isEmpty()) {
            Department newDepartment = new Department("new Department");
            User user = new User();
            user.setUserName("place");
            newDepartment.setManager(user);
            addDepartment(newDepartment);
            tmp.add(newDepartment);
        }
        return tmp;
    }

    /**
     * Gets the departmentList
     *
     * @return a list of all departments
     */
    public ObservableList<Department> getAllDepartments() {
        return departmentList;
    }

    /**
     * Adds a department to the departmentList
     *
     * @param department the department you want to add
     */
    public void addDepartment(Department department) {
        departmentDAL.addDepartment(department);
    }

    /**
     * Removes the given department from the departmentList
     *
     * @param department
     */
    public void removeDepartment(Department department) {
        departmentList.remove(department);
    }

    /**
     * Edits the given department in the Database
     *
     * @param department the updated department
     */
    public void editDepartment(Department department) {
        departmentDAL.updateDepartment(department);
    }

    /**
     * Deletes the given department from the Database
     *
     * @param department the department you want to delete from the database
     * @throws SQLException
     */
    public void deleteDepartment(Department department) throws SQLException {
        departmentDAL.deleteDepartment(department);
    }

    /**
     * Adds a department subDepartment association to the database
     * @param department the superDepartment
     * @param subDepartment the subDepartment
     */
    public void addSubDepartment(Department department, Department subDepartment) {
        departmentDAL.addSubDepartment(department, subDepartment);
    }

//TODO add javadoc to this
    public void exportPhoneNumbers(List<Department> departments) {
        departmentDAL.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(List<Department> departments, String outputFile) {
        departmentDAL.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(List<Department> departments, File outputFile) {
        departmentDAL.exportPhoneNumbers(departments, outputFile);
    }
}
