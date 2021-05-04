package DAL;

import BE.Screen;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAL {

    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();


    public void deleteScreen(Screen screen) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Screen WHERE Id=?");
            pSql.setInt(1,screen.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void updateScreen(Screen newScreen, Screen oldScreen){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Screen SET ScreenName=?, ScreenInfo=? WHERE Id=?");
            pSql.setString(1, newScreen.getName());
            pSql.setString(2, newScreen.getScreenInfo());
            pSql.setInt(3,oldScreen.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addScreen(Screen newScreen) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Screen VALUES(?,?)");
            pSql.setString(1, newScreen.getName());
            pSql.setString(2, newScreen.getScreenInfo());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Screen> getScreens(){
        List<Screen> allScreens = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Screen");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()){
                int id = rs.getInt("Id");
                String screenName = rs.getString("ScreenName");
                String screenInfo = rs.getString("ScreenInfo");

                allScreens.add(new Screen(id,screenName, screenInfo));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allScreens;
    }

    public void assignScreenRights(User user, Screen screen){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenRights VALUES(?,?)");
            pSql.setInt(1, screen.getId());
            pSql.setString(2, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeScreenRights(User user, Screen screen){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=?");
            pSql.setString(1, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
