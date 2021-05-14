package GUI.Model;

import BE.Bug;
import BLL.BugManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class BugModel {

    private static BugModel instance;
    private ObservableList<Bug> allBugs;
    private BugManager bugManager;

    public BugModel() {
        initialize();
    }

    /**
     * Initialize the class.
     */
    private void initialize() {
        bugManager = new BugManager();
        allBugs = FXCollections.observableArrayList();
        try {
            allBugs.addAll(bugManager.getBugs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Add a new Bug report.
     * @param newBug
     */
    public void addBug(Bug newBug) {
        bugManager.addBug(newBug);
        updateAllBugs();
    }


    /**
     * Get all the Bug reports.
     * @return Returns a List of Bug reports.
     */
    public ObservableList<Bug> getAllBugs() {
        return allBugs;
    }


    /**
     * Update an existing Bug reported.
     * @param oldBug The Bug report to update.
     * @param newBug The new Bug report to replace with.
     */
    public void updateBug(Bug oldBug, Bug newBug) {
        bugManager.updateBug(oldBug, newBug);
        updateAllBugs();
    }

    /**
     * Update the ObservableList of Bug reports.
     */
    public void updateAllBugs() {
        allBugs.clear();
        try {
            allBugs.addAll(bugManager.getBugs());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete a Bug report.
     * @param bug The Bug report to delete.
     */
    public void deleteBug(Bug bug) {
        bugManager.deleteBug(bug);
        updateAllBugs();
    }


    /**
     * Get the singleton instance.
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
            System.out.println(String.format("%s singleton was reset.", getClass().getSimpleName()));
        }
    }
}
