import BLL.EmailManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class EmailSendTest {

    @DisplayName("Email Send Test")
    @org.junit.jupiter.api.Test
    public void sendTest() {
        boolean sent = EmailManager.getInstance().sendTo("", "", "Test Email!", "This is sent from Java!");
        Assertions.assertEquals(sent, 0);
    }
}