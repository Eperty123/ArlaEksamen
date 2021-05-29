package GUI.Model;

import BE.*;
import BLL.BugManager;
import BLL.EmailManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class BugModel implements IBugCRUD {

    private static BugModel instance;
    private BugManager bugManager;

    public BugModel() {
        initialize();
    }

    /**
     * Initialize the class.
     */
    private void initialize() {
        bugManager = new BugManager();
    }

    /**
     * Add a new Bug report.
     *
     * @param newBug
     */
    @Override
    public void addBug(Bug newBug) throws SQLException {
        bugManager.addBug(newBug);
    }


    /**
     * Get all the Bug reports.
     *
     * @return Returns a List of Bug reports.
     */
    @Override
    public ObservableList<Bug> getAllBugs() {
        return bugManager.getAllBugs();
    }

    /**
     * Update an existing Bug reported.
     *
     * @param oldBug The Bug report to update.
     * @param newBug The new Bug report to replace with.
     */
    public void updateBug(Bug oldBug, Bug newBug) throws SQLException {
        bugManager.updateBug(oldBug, newBug);
    }

    /**
     * Delete a Bug report.
     *
     * @param bug The Bug report to delete.
     */
    @Override
    public void deleteBug(Bug bug) throws SQLException {
        bugManager.deleteBug(bug);
    }

    /**
     * Get the singleton instance.
     *
     * @return Returns a singleton instance of this class.
     */
    public static BugModel getInstance() {
        return instance == null ? instance = new BugModel() : instance;
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }
}
