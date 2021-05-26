package BLL;

import BE.Department;
import DAL.DepartmentDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DepartmentManager {
    private ObservableList<Department> departmentList = FXCollections.observableArrayList();
    private DepartmentDAL departmentDAL = new DepartmentDAL();

    public DepartmentManager() {
        departmentList.setAll(departmentDAL.getDepartments());
    }

    public Department getSuperDepartment() {
        ObservableList<Department> tmp = FXCollections.observableArrayList(departmentList);
        departmentList.forEach(d -> {
            d.getSubDepartments().forEach(sd->tmp.remove(sd));
        });
        return tmp.get(0);
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

    public void deleteDepartment(Department d) {
        departmentDAL.deleteDepartment(d);
    }

    public void addSubDepartment(Department department, Department subDepartment){
        departmentDAL.addSubDepartment(department,subDepartment);
    }
}
