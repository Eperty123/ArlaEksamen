import BE.Department;
import BLL.DepartmentExtension;
import BE.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class PhoneListExportTest {
    @DisplayName("Phone Number Export Test")
    @org.junit.jupiter.api.Test
    public void sendTest() {
        var departmentList = new ArrayList<Department>();
        var department = new Department(1, "Department Test");
        var department1 = new Department(1, "Administration");
        var user = new User("Test");
        var user1 = new User("Test 1");

        department.addUser(user);
        department1.addUser(user);

        departmentList.add(department);
        departmentList.add(department1);

        Assertions.assertDoesNotThrow(() -> {
            DepartmentExtension.exportPhoneNumbers(departmentList);
            System.out.println(department.getUsers().size());
        });
    }
}