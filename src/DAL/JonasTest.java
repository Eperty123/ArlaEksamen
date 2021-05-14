package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.List;

public class JonasTest {


    public static void main(String[] args) {

        MessageDAL messageDAL = new MessageDAL();

        List<User> users = UserModel.getInstance().getAllUsers();
        List<ScreenBit> screenBits = ScreenModel.getInstance().getAllScreenBits();

        Message msg = new Message( LocalDateTime.now(), LocalDateTime.now().plusHours(1), "testtest", Color.AZURE, MessageType.Manager);

        messageDAL.addMessage(users.get(1), msg, screenBits );



    }

}
