package BLL;

import BE.*;
import GUI.Model.DataModel;
import GUI.Model.UserModel;

import java.util.regex.Pattern;

public class EmailExtension {

    /**
     * Check if the specified string is a valid email.
     *
     * @param email The email string to check.
     * @return Returns true if yes otherwise false.
     */
    public static boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    /**
     * Send an email about a bug report to all the admins.
     *
     * @param bug       The bug to send an email about.
     * @param screenBit The associated screen.
     * @param user      The user who sends the email.
     */
    public static void sendEmailBugReportToAllAdmins(Bug bug, ScreenBit screenBit, User user) {
        if (bug != null && user != null) {
            var admins = DataModel.getInstance().getAllUsersByRole(UserType.Admin);
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

}
