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

    @Override
    public boolean hasBugsLoaded() {
        return bugManager.hasBugsLoaded();
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
        }
    }
}
