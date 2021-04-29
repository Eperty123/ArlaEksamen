package BLL;

public class PasswordManager {


    public int encrypt(String password){
        return password.hashCode();
    }

}
