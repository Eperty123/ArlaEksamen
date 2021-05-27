package BLL;

import BE.Department;
import DAL.DepartmentDAL;
import GUI.Model.DepartmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class DepartmentManager {
    private final ObservableList<Department> departmentList = FXCollections.observableArrayList();
    private final DepartmentDAL departmentDAL = new DepartmentDAL();

    public DepartmentManager() {
        departmentList.setAll(departmentDAL.getDepartments());
    }

    public List<Department> getSuperDepartment() {
        ObservableList<Department> tmp = FXCollections.observableArrayList(departmentList);
        departmentList.forEach(d -> {
            d.getSubDepartments().forEach(sd -> tmp.remove(sd));
        });
        if(departmentList.isEmpty())
        {
            Department newDepartment = new Department("new Department1");
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

    public void deleteDepartment(Department d) {
        departmentDAL.deleteDepartment(d);
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        departmentDAL.addSubDepartment(department, subDepartment);
    }
}
