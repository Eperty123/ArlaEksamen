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

    public void addTitle(String newTitle) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Title VALUES(?)");
            pSql.setString(1, newTitle);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new title " +
//                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void deleteTitle(String title) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            try {
                deleteUserTitleAssociations(con, title);
                PreparedStatement pSql = con.prepareStatement("DELETE FROM Title WHERE Id=?");
                pSql.setString(1, title);
                pSql.execute();
            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    private void deleteUserTitleAssociations(Connection con, String title) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("DELETE FROM UserTitle WHERE Title=?");
        pSql.setString(1, title);
        pSql.execute();
    }

    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Title");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                titles.add(rs.getString("Title"));
            }

            return titles;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //throw throwables;
            return null;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to retrieve all titles " +
//                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void updateTitle(String oldTitle, String title) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Title SET Title=? WHERE Title=?");
            pSql.setString(1, title);
            pSql.setString(2, oldTitle);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

}
