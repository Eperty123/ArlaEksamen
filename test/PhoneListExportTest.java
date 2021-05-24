import BE.Department;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

public class PhoneListExportTest {
    @DisplayName("Phone Number Export Test")
    @org.junit.jupiter.api.Test
    public void sendTest() {
        var department = new Department(1, "Department Test");
        department.getUsers().add(UserModel.getInstance().getUser("admtest"));
        System.out.println(department.getUsers().size());
        Assertions.assertDoesNotThrow(() -> {
            DepartmentModel.getInstance().exportPhoneNumbers(department);
        });
    }
}