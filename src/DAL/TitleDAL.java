package DAL;


import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TitleDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

    public void addTitle(String newTitle){
        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Title VALUES(?)");
            pSql.setString(1, newTitle);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new title " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void deleteTitle(String title){

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Title WHERE Title=?");
            pSql.setString(1, title);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a title " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }



    public List<String> getTitles(){
        List<String> titles = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Title");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while(rs.next()){
                titles.add(rs.getString("Title"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to retrieve all titles " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return titles;
    }

    public void updateTitle(String oldTitle, String title){

        try(Connection con = dbCon.getConnection()){
            updateUserTitles(con, oldTitle, title);
            PreparedStatement pSql = con.prepareStatement("UPDATE Title SET Title=? WHERE Title=?");
            pSql.setString(1, title);
            pSql.setString(2, oldTitle);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a title " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    private void updateUserTitles(Connection con, String oldTitle, String title) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("UPDATE [User] SET Title=? WHERE Title=?");
        pSql.setString(1, title);
        pSql.execute();

    }

}
