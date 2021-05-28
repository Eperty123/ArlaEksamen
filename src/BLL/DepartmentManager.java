package BLL;

import BE.Department;
import BE.IDepartmentCRUD;
import BE.User;
import DAL.DepartmentDAL;
import GUI.Model.DataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class DepartmentManager implements IDepartmentCRUD {
    private ObservableList<Department> departmentList;
    private final DepartmentDAL departmentDAL = new DepartmentDAL();

    private boolean departmentLoaded;

    public DepartmentManager() {
        initialize();
    }

    /**
     * Gets the departments that are not subDepartments, if there are no departments in the database it returns a new Department
     *
     * @return A list of super departments
     */

    private void initialize() {
        var departments = departmentDAL.getDepartments();
        if (departments != null) {
            departmentList = FXCollections.observableArrayList();
            departmentList.addAll(departmentDAL.getDepartments());
            departmentLoaded = true;
        }
    }

    @Override
    public List<Department> getSuperDepartments() {
        try {
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
                return tmp;
            }
        } catch (SQLException throwables) {
            return null;
        }
        return null;
    }

    /**
     * Gets the departmentList
     *
     * @return a list of all departments
     */
    public ObservableList<Department> getAllDepartments() {
        return departmentList;
    }

    public void addDepartment(Department department) throws SQLException {
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
    public void editDepartment(Department department) throws SQLException {
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

    /**
     * Adds a department's subDepartment association to the database
     *
     * @param department    the superDepartment
     * @param subDepartment the subDepartment
     */
    public void addSubDepartment(Department department, Department subDepartment) throws SQLException {
        departmentDAL.addSubDepartment(department, subDepartment);
    }

    public boolean hasDepartmentsLoaded() {
        return departmentLoaded;
    }
}
