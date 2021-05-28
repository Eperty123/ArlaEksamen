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

    public ObservableList<Department> getAllDepartments() {
        return departmentList;
    }

    public void addDepartment(Department department) {
        departmentDAL.addDepartment(department);
    }

    public void removeDepartment(Department department) {
        departmentList.remove(department);
    }

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

    public void deleteDepartment(Department d) throws SQLException {
        departmentDAL. deleteDepartment(d);
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        departmentDAL.addSubDepartment(department, subDepartment);
    }
}
