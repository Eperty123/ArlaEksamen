import BE.User;
import DAL.Parser.UserBackUp;
import DAL.UserDAL;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class UserBackupTest {
    @DisplayName("User Import Test")
    @org.junit.jupiter.api.Test
    public void importTest() {
        var userImporter = new UserBackUp();
        var importedUsers = userImporter.importUsers("src/Resources/User_backup_2021-05-25_2045305923.csv");
        UserModel.getInstance().addUsers(importedUsers);
        System.out.println(String.format("%s users were imported!", importedUsers.size()));
        Assertions.assertNotEquals(0, importedUsers.size());
    }

    @DisplayName("User Export Test")
    @org.junit.jupiter.api.Test
    public void exportTest() {
        var userExporter = new UserBackUp();
        var desiredDepartment = DepartmentModel.getInstance().getDepartment(555);
        userExporter.exportUsers(desiredDepartment);
        //Assertions.assertNotEquals(0, importedUsers.size());
    }
}