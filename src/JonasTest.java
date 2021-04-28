import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.List;

public class JonasTest {






    public static void main(String[] args) throws SQLException {
        UserDAL userDAL = new UserDAL();
        List<User> users = userDAL.getUsers();

        for(User u : users){
            System.out.println(users);
        }

    }
}
