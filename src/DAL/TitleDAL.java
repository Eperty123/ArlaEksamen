package DAL;

import BE.Title;
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

    public void deleteTitle(Title title){

        try(Connection con = dbCon.getConnection()){
            deleteUserTitleAssociations(con, title);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Title WHERE Id=?");
            pSql.setInt(1, title.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a title " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    private void deleteUserTitleAssociations(Connection con, Title title) throws SQLException {

            PreparedStatement pSql = con.prepareStatement("DELETE FROM UserTitle WHERE TitleId=?");
            pSql.setInt(1, title.getId());
            pSql.execute();
    }

    public List<Title> getTitles(){
        List<Title> titles = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Title");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while(rs.next()){
                titles.add(new Title(rs.getInt("Id"), rs.getString("Title")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to retrieve all titles " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return titles;
    }

    public void updateTitle(Title oldTitle, Title title){

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("UPDATE Title SET Title=? WHERE Id=?");
            pSql.setString(1, title.getTitle());
            pSql.setInt(2, oldTitle.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a title " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

}
