import BE.User;
import DAL.Parser.UserBackUp;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class UserBackupTest {
    @DisplayName("User Import Test")
    @org.junit.jupiter.api.Test
    public void importTest() {
        var userImporter = new UserBackUp();
        userImporter.importUsers("src/Resources/User_backup_2021-05-25_303449149.csv");
    }

    @DisplayName("User Export Test")
    @org.junit.jupiter.api.Test
    public void exportTest() {
        var userExporter = new UserBackUp();
        var desiredDepartment = DepartmentModel.getInstance().getDepartment(555);
        //userExporter.exportUsers(desiredDepartment);
        System.out.println(desiredDepartment.getUsers().size());
    }
}