package BLL;

import BE.Bug;
import BE.IBugCRUD;
import DAL.BugDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class BugManager implements IBugCRUD {

    BugDAL bugDAL = new BugDAL();
    private final ObservableList<Bug> bugsList = FXCollections.observableArrayList();

    public BugManager() {
        initialize();
    }

    private void initialize() {
        bugsList.addAll(bugDAL.getAllBugs());
    }

    /**
     * Gets a list of all the bugs from the database.
     *
     * @return a list of bugs from the database.
     */
    @Override
    public ObservableList<Bug> getAllBugs() {
        return bugsList;
    }

    /**
     * Adds a bug to the Database.
     *
     * @param bug the given bug to add.
     */
    @Override
    public void addBug(Bug bug) throws SQLException {
        bugDAL.addBug(bug);
    }

    /**
     * Updates a bug in the database.
     *
     * @param oldBug     the old bug to be updated.
     * @param updatedBug the updated bug.
     */
    @Override
    public void updateBug(Bug oldBug, Bug updatedBug) throws SQLException {
        bugDAL.updateBug(oldBug, updatedBug);
    }

    /**
     * Deletes a bug from the database.
     *
     * @param bug the bug to be deleted.
     */
    @Override
    public void deleteBug(Bug bug) throws SQLException {
        bugDAL.deleteBug(bug);
    }
}
