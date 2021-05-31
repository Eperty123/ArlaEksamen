package BE;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    static Department department = new Department(-1, "DPT");
    static List<User> userList = new ArrayList<>();
    static List<Department> subDepartments = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < 2; i++) {
            subDepartments.add(new Department(i, "SUBDPT"));
        }
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setId(i);
            userList.add(u);
            if (u.getId() % 2 == 0)
                subDepartments.get(0).addUser(u);
            else
                subDepartments.get(1).addUser(u);
        }

        department.setSubDepartments(FXCollections.observableArrayList(subDepartments));
    }


    @Test
    void getUsers() {
        assertTrue(subDepartments.get(0).getUsers().size() == 5
                &&
                subDepartments.get(1).getUsers().size() == 5, "Users get added to the proper classes");

        assertTrue(subDepartments.get(0).getUsers().stream().allMatch(u -> u.getId() % 2 == 0)
                &&
                !subDepartments.get(1).getUsers().stream().allMatch(u -> u.getId() % 2 == 0), "The specific users i add get added to the specific department");
    }

    @Test
    void getSubDepartments() {
        assertTrue(department.getSubDepartments().size() == 2, "Departments work properly");
    }

    @Test
    void getAllSubDepartments() {

        department.getSubDepartments().get(0).getSubDepartments().add(new Department(-1, "subsubDpt"));

        assertTrue(department.getAllSubDepartments().size() == 3, "Sub sub departments work properly");
    }
}