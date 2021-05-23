package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import DAL.Parser.UserBackUp;
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
        UserBackUp userBackUp = new UserBackUp();

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



        userBackUp.backUpUsers(users);







    }

}
