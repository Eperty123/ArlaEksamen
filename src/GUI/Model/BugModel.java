package GUI.Model;

import BE.Bug;
import BE.User;
import BLL.BugManager;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public class BugModel {

    private static BugModel instance;
    private ObservableList<Bug> allBugs;
    private BugManager bugManager;

    public BugModel() {
        bugManager = new BugManager();
        allBugs = FXCollections.observableArrayList();
        allBugs.addAll(new Bug("TEEEEEST", LocalDate.now().toString()));
    }

    public static BugModel getInstance() {
        return instance == null ? instance = new BugModel() : instance;
    }


    public void addBug(Bug newBug) {
        bugManager.addBug(newBug);
        updateAllBugs();
    }


    public ObservableList<Bug> getAllBugs() {
        return allBugs;
    }


    public void updateBug(Bug oldBug, Bug newBug) {
        bugManager.updateBug(oldBug, newBug);
        updateAllBugs();
    }

    public void updateAllBugs() {
        allBugs.clear();
        try {
            allBugs.addAll(bugManager.getBugs());
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteBug(Bug bug){
        bugManager.deleteBug(bug);
        updateAllBugs();
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
