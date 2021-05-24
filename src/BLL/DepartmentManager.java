package BLL;

import BE.Department;
import BE.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;

public class DepartmentManager {
    private Department department = new Department(-1, "SuperDepartment");
    private ObservableList<Department> departmentList = FXCollections.observableArrayList(department);

    public DepartmentManager() {
        department.getUsers().add(new User("doku", "poker", "denn062g@easv365.dk", 112));
        department.getUsers().add(new User("doku3", "poker3", "denn062g@easv365.dk", 112));
        department.getUsers().add(new User("doku69", "poker3", "denn062g@easv365.dk", 112));

        for (int i = 0; i < 2; i++) {
            Department department2 = new Department(i, "subDepartment" + i);
            departmentList.add(department2);
            department2.setSubDepartment(true);
            department.getSubDepartments().add(department2);
        }
        department.getSubDepartments().get(0).getUsers().add(new User("Miku", "QWERTY", "MikuMain@SOMEDomain.com", 69));
        department.getSubDepartments().get(1).getUsers().add(new User("C", "Sharp", "C_the_man@SOMEDomain.com", 69420));
        departmentList.add(new Department(-1, "super dep 2"));
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

    public void editDepartment(Department department){
    }
}
