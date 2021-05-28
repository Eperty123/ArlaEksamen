import DAL.Parser.UserBackUp;
import GUI.Model.DepartmentModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;

public class UserBackupTest {
    @DisplayName("User Import Test")
    @org.junit.jupiter.api.Test
    public void importTest() {
        var userImporter = new UserBackUp();
        try {
            var importedUsers = userImporter.importUsers("src/Resources/User_backup_2021-05-25_2045305923.csv");
            Assertions.assertNotEquals(0, importedUsers.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
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