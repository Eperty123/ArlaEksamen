package BLL;

import BE.Bug;
import BE.IBugCRUD;
import DAL.BugDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class BugManager implements IBugCRUD {

    BugDAL bugDAL = new BugDAL();
    private ObservableList<Bug> bugsList;
    private boolean bugsLoaded;

    public BugManager() {
        initialize();
    }

    private void initialize() {
        var bugs = bugDAL.getAllBugs();
        if (bugs != null) {
            bugsList = FXCollections.observableArrayList();
            bugsList.addAll(bugs);
            bugsLoaded = true;
        }
    }

    /**
     * Gets a list of all the bugs from the database.
     *
     * @return a list of bugs from the database.
     */
    public ObservableList<Bug> getAllBugs() {
        return bugsList;
    }

    /**
     * Adds a bug to the Database.
     *
     * @param bug the given bug to add.
     */
    public void addBug(Bug bug) throws SQLException {
        bugDAL.addBug(bug);
    }

    /**
     * Updates a bug in the database.
     *
     * @param oldBug     the old bug to be updated.
     * @param updatedBug the updated bug.
     */
    public void updateBug(Bug oldBug, Bug updatedBug) throws SQLException {
        bugDAL.updateBug(oldBug, updatedBug);
    }

    /**
     * Deletes a bug from the database.
     *
     * @param bug the bug to be deleted.
     */
    public void deleteBug(Bug bug) throws SQLException {
        bugDAL.deleteBug(bug);
    }

    @Override
    public boolean hasBugsLoaded() {
        return bugsLoaded;
    }
}
