package DAL;

import BE.*;
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
import java.sql.SQLOutput;
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
        DepartmentDAL departmentDAL = new DepartmentDAL();

        List<User> users = UserModel.getInstance().getAllUsers();
        List<ScreenBit> screenBits = ScreenModel.getInstance().getAllScreenBits();
        List<Department> departments = departmentDAL.getDepartments();

        System.out.println(departments.size() + " departments");
        System.out.println("----------------------------");

        for(Department d : departments){
            System.out.println("Department: " + d.getName());
            System.out.println();

            System.out.println("Users:");
            for(User u : d.getUsers()){
                System.out.println(u.getUserName());
            }
            System.out.println();
            if (d.getManager() != null){
                System.out.println("Manager: " + d.getManager().getUserName());
                System.out.println("ManagerID: " + d.getManager().getId());
            }
            System.out.println("------------------------------");
            System.out.println();

        }


        /*
        try (CSVReader reader = new CSVReader(new FileReader("src/Resources/MOCK_USERS2.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

         */









    }

    public void addUsersFromCSV(File csvFile){
        List<String[]> rows = CSVParser.parseFile(csvFile.getAbsolutePath()).getParsedData();
        List<User> users = new ArrayList<>();

    }

}
