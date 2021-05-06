import BE.User;
import BLL.PasswordManager;
import DAL.UserDAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserTest {



    @Test
    public void passwordEncryptionTest(){
        PasswordManager passwordManager = new PasswordManager();

        String password = "test";
        int encryptedPassword = passwordManager.encrypt(password);

        String expectedPassword = password;
        String actualPassword = String.valueOf(encryptedPassword);

        Assertions.assertNotEquals(expectedPassword, actualPassword);

    }



}
