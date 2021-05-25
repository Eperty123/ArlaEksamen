import BE.Department;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.ArrayList;
public class PhoneListExportTest {
    @DisplayName("Phone Number Export Test")
    @org.junit.jupiter.api.Test
    public void sendTest() {
        var departmentList = new ArrayList<Department>();
        var department = new Department(1, "Department Test");
        var department1 = new Department(1, "Administration");
        department.getUsers().add(UserModel.getInstance().getUser("admtest"));
        department1.getUsers().add(UserModel.getInstance().getUser("mgrtest"));

        departmentList.add(department);
        departmentList.add(department1);

        Assertions.assertDoesNotThrow(() -> {
            DepartmentModel.getInstance().exportPhoneNumbers(departmentList);
            System.out.println(department.getUsers().size());
        });
    }
}