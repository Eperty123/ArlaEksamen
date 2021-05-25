package BLL;

import BE.Department;
import BE.User;
import DAL.DepartmentDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DepartmentManager {
    private Department department = new Department(-1, "SuperDepartment");
    private ObservableList<Department> departmentList = FXCollections.observableArrayList(department);
    private DepartmentDAL departmentDAL = new DepartmentDAL();

    public DepartmentManager() {
        departmentList.setAll(departmentDAL.getDepartments());
    }


    public ObservableList<Department> getSuperDepartments() {
        ObservableList<Department> tmp = FXCollections.observableArrayList();
        departmentList.forEach(d -> {
            if (!d.getIsSubDepartment())
                tmp.add(d);
        });
        return tmp;
    }

    public ObservableList<Department> getAllDepartments() {
        return departmentList;
    }

    public void addDepartment(Department department) {
        departmentList.add(department);
    }

    public void removeDepartment(Department department) {
        departmentList.remove(department);
    }

    public void editDepartment(Department department) {
    }

    public void exportPhoneNumbers(List<Department> departments) {
        departmentDAL.exportPhoneNumbers(departments);
    }

    public void deleteDepartment(Department d) {
        departmentDAL.deleteDepartment(d);
    }
}
