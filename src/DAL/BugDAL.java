package DAL;

import BE.Bug;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void deleteBug(Bug bug) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Bug WHERE Id=?");
            pSql.setInt(1, bug.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a bug from " +
                    "the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Update an existing Bug report in the database with values of another.
     *
     * @param newBug The Bug report to use information of.
     * @param oldBug The existing Bug report to update.
     */
    public void updateBug(Bug newBug, Bug oldBug) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Bug SET DateRegistered=?, ResolvedStatus=?, Description=?, ScreenId=?, UserId=?, FixMessage=? WHERE Id=?");
            pSql.setString(1, newBug.getDateReported());
            pSql.setInt(2, newBug.isBugResolved() ? 1 : 0);
            pSql.setString(3, oldBug.getDescription());
            pSql.setInt(4, oldBug.getReferencedScreen().getId());
            pSql.setInt(5, oldBug.getAdminResponsible().getId());
            pSql.setInt(6, oldBug.getId());
            pSql.setString(7, newBug.getFixMessage());
            //System.out.println(String.format("Bug update status: %s", pSql.executeUpdate() != 0 ? "successful" : "failed"));

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a bug " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Create a new Bug report in the database.
     *
     * @param newBug The Bug report instance to insert into the database.
     */
    public void addBug(Bug newBug) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Screen VALUES(?,?,?,?,?)");
            pSql.setString(1, newBug.getDescription());
            pSql.setString(2, newBug.getDateReported());
            pSql.setInt(2, newBug.getReferencedScreen().getId());
            pSql.setInt(4, newBug.getAdminResponsible().getId());
            pSql.execute();

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a bug " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
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

            PreparedStatement pSql = con.prepareStatement("SELECT\n" +
                    "\tBug.*, \n" +
                    "\t[User].Id, \n" +
                    "\t[User].FirstName, \n" +
                    "\t[User].LastName, \n" +
                    "\t[User].Email, \n" +
                    "\t[User].Password, \n" +
                    "\t[User].UserRole\n" +
                    "FROM\n" +
                    "\tBug\n" +
                    "\tINNER JOIN\n" +
                    "\t[User]\n" +
                    "\tON \n" +
                    "\t\tBug.UserId = [User].Id;");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {

                Bug bug = resultSetParser.getBug(rs);
                allBugs.add(bug);
            }
        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all bugs from " +
                    "the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return allBugs;
    }
}
