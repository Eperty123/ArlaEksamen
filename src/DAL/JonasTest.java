package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class JonasTest {


    public static void main(String[] args) throws SQLException {

        MessageDAL messageDAL = new MessageDAL();
        ScreenDAL screenDAL = new ScreenDAL();

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



        LocalDateTime start = LocalDateTime.of(2021,05,15,23,0);
        LocalDateTime end = start.plusHours(8).plusMinutes(0);

        Message msg = new Message( start, end, "testtesasdt", Color.AZURE, MessageType.Manager);


        LocalDateTime startTime = LocalDateTime.of(2021,05,17,19,30);
        LocalDateTime endTime = LocalDateTime.of(2021,05,17,20,30);


        for(ScreenBit s : screenBits){
            if(s.getId() == 1010){
                System.out.println(s.isAvailable(startTime, endTime));
            }
        }







    }

}
