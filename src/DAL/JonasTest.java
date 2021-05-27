package DAL;

import BE.*;
import DAL.DbConnector.DbConnectionHandler;
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
import java.sql.Connection;
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
        DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

        List<User> users = UserModel.getInstance().getAllUsers();
        List<ScreenBit> screenBits = ScreenModel.getInstance().getAllScreenBits();
        List<Department> departments = departmentDAL.getDepartments();

        try(Connection con = dbCon.getConnection()){

            for(ScreenBit s : screenBits){
                screenDAL.createScreenBitTimeTable(con, s.getId());
            }

        }
    }



}
