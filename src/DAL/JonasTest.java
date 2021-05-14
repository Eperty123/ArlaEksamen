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



        /*
        for(ScreenBit s : screenBits){
            for (Map.Entry<LocalDateTime, Boolean> localDateTimeBooleanEntry : s.getTimeTable().entrySet()) {
                Map.Entry pair = (Map.Entry) localDateTimeBooleanEntry;
                System.out.println(pair.getKey() + " available: " + pair.getValue());
            }

        }

         */

        LocalDateTime start = LocalDateTime.of(2021,05,15,14,00);
        LocalDateTime end = start.plusHours(9).plusMinutes(30);

        Message msg = new Message( start, end, "testtesasdt", Color.AZURE, MessageType.Manager);

        System.out.println(((end.getHour()- start.getHour()) * 2) + ((end.getMinute()-start.getMinute() == 0 ? 0 : 1)));
        //messageDAL.addMessage();


    }

}
