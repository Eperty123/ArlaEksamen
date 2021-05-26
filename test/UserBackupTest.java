import DAL.Parser.UserBackUp;
import GUI.Model.DepartmentModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class UserBackupTest {
    @DisplayName("User Import Test")
    @org.junit.jupiter.api.Test
    public void importTest() {
        var userImporter = new UserBackUp();
        var importedUsers = userImporter.importUsers("src/Resources/User_backup_2021-05-25_2045305923.csv");
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