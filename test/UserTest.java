import BE.User;
import BLL.LoginManager;
import DAL.UserDAL;
import GUI.Controller.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserTest {

    @DisplayName("Login with valid info should succeed")
    @Test
    public void validInfoLoginTest(){
        LoginManager loginManager = new LoginManager();

        // Actual login info for the admin test user in the database.
        String adminUserName = "admtest";
        String adminPassword = "testa";

        boolean expectedResult = true;
        boolean actualResult = loginManager.attemptLogin(adminUserName, adminPassword);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @DisplayName("Login with invalid info should fail")
    @Test
    public void invalidInfoLoginTest(){
        LoginManager loginManager = new LoginManager();

        // Username and password does not match a user in the database.
        String adminUserName = "wrongName";
        String adminPassword = "wrongPassword";

        boolean expectedResult = false;
        boolean actualResult = loginManager.attemptLogin(adminUserName, adminPassword);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @DisplayName("Correct username, wrong password login should fail")
    @Test
    public void wrongPasswordLoginTest(){
        LoginManager loginManager = new LoginManager();

        // Username exist in the database, password does not match.
        String adminUserName = "admtest";
        String adminPassword = "wrongPassword";

        boolean expectedResult = false;
        boolean actualResult = loginManager.attemptLogin(adminUserName, adminPassword);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @DisplayName("Wrong username, correct password login should fail")
    @Test
    public void wrongUsernameLoginTest(){
        LoginManager loginManager = new LoginManager();

        // Username does not exist in the database, password does match the admin user.
        String adminUserName = "wrongUsername";
        String adminPassword = "testa";

        boolean expectedResult = false;
        boolean actualResult = loginManager.attemptLogin(adminUserName, adminPassword);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @DisplayName("Login with no info should fail")
    @Test
    public void noInfoLoginTest(){
        LoginManager loginManager = new LoginManager();

        // No username or password has been entered.
        String adminUserName = "";
        String adminPassword = "";

        boolean expectedResult = false;
        boolean actualResult = loginManager.attemptLogin(adminUserName, adminPassword);

        Assertions.assertEquals(expectedResult, actualResult);
    }



}
