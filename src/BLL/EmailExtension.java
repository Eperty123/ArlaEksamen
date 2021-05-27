package BLL;

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
}
