package DAL;

import BE.Bug;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BugDAL {
    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();

    /**
     * Delete a Bug report from the database.
     *
     * @param bug The Bug report to delete.
     */
    public void deleteBug(Bug bug) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Bug WHERE Id=?");
            pSql.setInt(1, bug.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Update an existing Bug report in the database with values of another.
     *
     * @param newBug The Bug report to use information of.
     * @param oldBug The existing Bug report to update.
     */
    public void updateBug(Bug newBug, Bug oldBug) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("UPDATE Bug SET Description=?, ResolvedStatus=?, FixMessage=?, AssignedAdmin=? WHERE Id=?");
            pSql.setString(1, newBug.getDescription());
            pSql.setInt(2, newBug.isBugResolved() ? 1 : 0);
            pSql.setString(3, newBug.getFixMessage().equalsIgnoreCase("No Fix Yet") ? "No Fix Yet" : newBug.getFixMessage());
            pSql.setInt(4, newBug.getAdminId());
            pSql.setInt(5, oldBug.getId());
            pSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Create a new Bug report in the database.
     *
     * @param newBug The Bug report instance to insert into the database.
     */
    public void addBug(Bug newBug) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Bug VALUES(?,?,?,?,?,?,?)");
            pSql.setString(1, newBug.getDescription());
            pSql.setString(2, "No fix yet");
            pSql.setTimestamp(3, Timestamp.valueOf(newBug.getDateReported()));
            pSql.setString(4, newBug.getReferencedScreen());
            pSql.setString(5, newBug.getReferencedUser());
            pSql.setInt(6, newBug.getAdminId() != 0 ? newBug.getAdminId() : 0);
            pSql.setInt(7, 0);
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Get all Bug reports from the database.
     *
     * @return Returns a List of Bug reports.
     */
    public List<Bug> getAllBugs() {
        List<Bug> allBugs = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Bug");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {

                Bug bug = resultSetParser.getBug(rs);
                allBugs.add(bug);
            }

            return allBugs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
