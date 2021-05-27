package GUI.Model;

import BE.*;
import BLL.BugManager;
import BLL.EmailManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BugModel {

    private static BugModel instance;
    private ObservableList<Bug> allBugs;
    private ObservableList<Bug> unresolvedBugs;
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
        unresolvedBugs = FXCollections.observableArrayList();
        updateAllBugs();
    }

    /**
     * Add a new Bug report.
     *
     * @param newBug
     */
    public void addBug(Bug newBug) {
        bugManager.addBug(newBug);
        updateAllBugs();
    }


    /**
     * Get all the Bug reports.
     *
     * @return Returns a List of Bug reports.
     */
    public ObservableList<Bug> getAllBugs() {
        return allBugs;
    }

    /**
     * Get all the unresolved Bug reports.
     *
     * @return Returns a List of Bug reports.
     */
    public ObservableList<Bug> getAllUnresolvedBugs() {
        return unresolvedBugs;
    }


    /**
     * Update an existing Bug reported.
     *
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
        try {
            var managerBugs = bugManager.getBugs();
            ArrayList<Bug> _unresolvedBugs = new ArrayList<>();
            allBugs.setAll(managerBugs);

            // Loop through the DAL bugs. This is to not let the Snackbar spam the admin about incoming bug reports for each bug.
            managerBugs.forEach((x) -> {
                if (!x.isBugResolved()) {
                    _unresolvedBugs.add(x);
                }
            });

            // Now set the unresolved bug ObservableList.
            unresolvedBugs.setAll(_unresolvedBugs);
            //System.out.println(String.format("Added unresolved bug: %s", unresolvedBugs.size()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete a Bug report.
     *
     * @param bug The Bug report to delete.
     */
    public void deleteBug(Bug bug) {
        bugManager.deleteBug(bug);
        updateAllBugs();
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
     * Send an email about a bug report to all the admins.
     *
     * @param bug       The bug to send an email about.
     * @param screenBit The associated screen.
     * @param user      The user who sends the email.
     */
    public void sendEmailBugReportToAllAdmins(Bug bug, ScreenBit screenBit, User user) {
        if (bug != null && user != null) {
            var admins = UserModel.getInstance().getAllUsersByRole(UserType.Admin);
            for (int i = 0; i < admins.size(); i++) {
                var admin = admins.get(i);

                var adminEmail = admin.getEmail().startsWith("@") ? admin.getEmail().substring(1, admin.getEmail().length() - 1) : admin.getEmail();
                var userEmail = user.getEmail().startsWith("@") ? user.getEmail().substring(1, user.getEmail().length() - 1) : user.getEmail();

                String emailRegex = "^(.+)@(.+)$";

                Pattern pattern = Pattern.compile(emailRegex);
                var emailMatcher = pattern.matcher(adminEmail);

                // If the email regex has matches, it must be a correct email format.
                if (emailMatcher.matches()) {

                    // Only send email if the following email providers are used in the admin mail. This is to avoid spam.
                    if (!emailMatcher.group(2).contains("gmail") && !emailMatcher.group(2).contains("yahoo") && !emailMatcher.group(2).contains("hotmail")
                            && !emailMatcher.group(2).contains("live") && !emailMatcher.group(2).contains("easv365")) {
                        System.out.println(String.format("%s's email: %s is not supported.", admin.getUserName(), admin.getEmail()));
                        continue;
                    }

                    // Send the bug report to the admin.
                    var bugEmail = new Email(adminEmail, userEmail, String.format("Bug on screen: %s", screenBit.getName()), bug.getDescription());
                    EmailManager.getInstance().sendTo(bugEmail);
                }
            }
        }
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
