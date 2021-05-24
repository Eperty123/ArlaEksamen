package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import DAL.Parser.CSVParser;
import DAL.Parser.UserBackUp;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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



        try (CSVReader reader = new CSVReader(new FileReader("src/Resources/MOCK_USERS2.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }










    }

    public void addUsersFromCSV(File csvFile){
        List<String[]> rows = CSVParser.parseFile(csvFile.getAbsolutePath()).getParsedData();
        List<User> users = new ArrayList<>();

    }

}
