package DAL;

import BE.*;
import DAL.DbConnector.DbConnectionHandler;
import DAL.Parser.UserBackUp;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class JonasTest {


    public static void main(String[] args) {

        MessageDAL messageDAL = new MessageDAL();
        ScreenDAL screenDAL = new ScreenDAL();
        UserBackUp userBackUp = new UserBackUp();
        DepartmentDAL departmentDAL = new DepartmentDAL();
        DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

        List<User> users = UserModel.getInstance().getAllUsers();
        List<ScreenBit> screenBits = ScreenModel.getInstance().getAllScreenBits();
        List<Department> departments = departmentDAL.getDepartments();



        ScreenBit screenBit = new ScreenBit("TstT");

        System.out.println(screenDAL.addScreenBit(screenBit));

    }



}
