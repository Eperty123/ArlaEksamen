import BE.User;
import DAL.UserDAL;

import java.sql.SQLException;
import java.util.List;

public class JonasTest {






    public static void main(String[] args) throws SQLException {
        UserDAL userDAL = new UserDAL();
        List<User> users = userDAL.getUsers();

        //User j1 = new User("Jonas", "Buus", "jbn", "j@test.dk", 34567, 1);
        //User j2 = new User("Jonas", "Fabricius", "jf", "j@tesst.dk", 345678, 0);

        //userDAL.addUser(new User("Jonas", "Buus", "jbn", "j@test.dk", 34567, 1));

        //userDAL.deleteUser(3);

        //userDAL.updateUser(users.get(1), j2);

        //System.out.println(users.get(1));

    }
}
