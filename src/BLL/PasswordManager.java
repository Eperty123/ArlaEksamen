package BLL;

public class PasswordManager {

    /**
     * Encrypts a String format password into an integer through Java's built in .hashCode() method.
     * @param password String to be encrypted.
     * @return encrypted password (int)
     */
    public int encrypt(String password){
        return password.hashCode();
    }

}
