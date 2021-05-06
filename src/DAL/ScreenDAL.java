package DAL;

import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ScreenDAL {

    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();


    public void deleteScreen(ScreenBit screenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Screen WHERE Id=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateScreen(ScreenBit newScreenBit, ScreenBit oldScreenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Screen SET ScreenName=?, ScreenInfo=? WHERE Id=?");
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.setInt(3, oldScreenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addScreen(ScreenBit newScreenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Screen VALUES(?,?)");
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public HashMap<ScreenBit, String> getScreens(){
        HashMap<ScreenBit, String> allScreens = new HashMap<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("" +
                    "SELECT  Screen.Id, Screen.ScreenName, " +
                    "Screen.ScreenInfo, ScreenRights.UserName " +
                    "FROM Screen " +
                    "LEFT JOIN ScreenRights " +
                    "ON Screen.Id = ScreenRights.ScreenId;");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()){
                int id = rs.getInt("Id");
                String screenName = rs.getString("ScreenName");
                String screenInfo = rs.getString("ScreenInfo");
                String assignedUser = rs.getString("UserName");

                ScreenBit screenBit = new ScreenBit(id,screenName, screenInfo);
                allScreens.put(screenBit, assignedUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allScreens;
    }


    public void assignScreenRights(User user, ScreenBit screenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenRights VALUES(?,?)");
            pSql.setInt(1, screenBit.getId());
            pSql.setString(2, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeScreenRights(User user, ScreenBit screenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=?");
            pSql.setString(1, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
