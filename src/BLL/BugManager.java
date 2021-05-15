package BLL;

import BE.Bug;
import DAL.BugDAL;

import java.sql.SQLException;
import java.util.List;

public class BugManager {

    BugDAL bugDAL = new BugDAL();

    /**
     * Gets a list of all the bugs from the database.
     * @return a list of bugs from the database.
     * @throws SQLException if the programme can't get access to the database.
     */
    public List<Bug> getBugs() throws SQLException {
        return bugDAL.getAllBugs();
    }

    /**
     * Adds a bug to the Database.
     * @param bug the given bug to add.
     */
    public void addBug(Bug bug) {
        bugDAL.addBug(bug);
    }

    /**
     * Updates a bug in the database.
     * @param oldBug the old bug to be updated.
     * @param updatedBug the updated bug.
     */
    public void updateBug(Bug oldBug, Bug updatedBug) {
        bugDAL.updateBug(oldBug, updatedBug);
    }

    /**
     * Deletes a bug from the database.
     * @param bug the bug to be deleted.
     */
    public void deleteBug(Bug bug) {
        bugDAL.deleteBug(bug);
    }
}
