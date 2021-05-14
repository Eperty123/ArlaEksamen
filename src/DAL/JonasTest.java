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
import java.util.Map;

public class JonasTest {


    public static void main(String[] args) {

        MessageDAL messageDAL = new MessageDAL();

        List<User> users = UserModel.getInstance().getAllUsers();
        List<ScreenBit> screenBits = ScreenModel.getInstance().getAllScreenBits();

        Message msg = new Message( LocalDateTime.now(), LocalDateTime.now().plusHours(1), "testtesasdt", Color.AZURE, MessageType.Manager);


        /*
        for(ScreenBit s : screenBits){
            for (Map.Entry<LocalDateTime, Boolean> localDateTimeBooleanEntry : s.getTimeTable().entrySet()) {
                Map.Entry pair = (Map.Entry) localDateTimeBooleanEntry;
                System.out.println(pair.getKey() + " available: " + pair.getValue());
            }

        }

         */

        LocalDateTime start = LocalDateTime.of(2021,05,4,14,30);
        LocalDateTime end = start.plusHours(5).plusMinutes(15);

        System.out.println(end.getHour()-start.getHour());
        System.out.println(end.getMinute()-start.getMinute());


    }

}
