package BLL;

import BE.Department;
import DAL.DepartmentDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public class DepartmentManager {
    private final ObservableList<Department> departmentList = FXCollections.observableArrayList();
    private final DepartmentDAL departmentDAL = new DepartmentDAL();

    public DepartmentManager() {
        this.departmentList.setAll(this.departmentDAL.getDepartments());
    }

    public List<Department> getSuperDepartment() {
        ObservableList<Department> tmp = FXCollections.observableArrayList(this.departmentList);
        this.departmentList.forEach(d -> {
            d.getSubDepartments().forEach(sd -> tmp.remove(sd));
        });
        if(this.departmentList.isEmpty())
        {
            Department newDepartment = new Department("new Department1");
            this.addDepartment(newDepartment);
            tmp.add(newDepartment);
        }
        return tmp;
    }

    public ObservableList<Department> getAllDepartments() {
        return this.departmentList;
    }

    public void addDepartment(Department department) {
        this.departmentDAL.addDepartment(department);
    }

    public void removeDepartment(Department department) {
        this.departmentList.remove(department);
    }

    public void editDepartment(Department department) {
        this.departmentDAL.updateDepartment(department);
    }

    public void exportPhoneNumbers(List<Department> departments) {
        this.departmentDAL.exportPhoneNumbers(departments);
    }

    public void exportPhoneNumbers(List<Department> departments, String outputFile) {
        this.departmentDAL.exportPhoneNumbers(departments, outputFile);
    }

    public void exportPhoneNumbers(List<Department> departments, File outputFile) {
        this.departmentDAL.exportPhoneNumbers(departments, outputFile);
    }

    public void deleteDepartment(Department d) {
        this.departmentDAL.deleteDepartment(d);
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        this.departmentDAL.addSubDepartment(department, subDepartment);
    }
}
