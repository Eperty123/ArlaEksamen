package DAL;

import BE.Bug;
import BE.ScreenBit;
import BE.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetParser {

    /**
     * Creates and returns a ScreenBit object from a ResultSet row.
     *
     * @param rs
     * @return ScreenBit object
     * @throws SQLException
     */
    public ScreenBit getScreenBit(ResultSet rs) throws SQLException {

        // Get ScreenBit info from ResultSet
        int screenId = rs.getInt("ScreenId");
        String name = rs.getString("ScreenName");
        String screenInfo = rs.getString("ScreenInfo");

        ScreenBit newScreenBit = new ScreenBit(screenId, name, screenInfo);

        return newScreenBit;
    }

    /**
     * Creates and returns a User object from a ResultSet row.
     *
     * @param rs
     * @return User object
     * @throws SQLException
     */
    public User getUser(ResultSet rs) throws SQLException {

        // Get assigned User info from ResultSet

        int userId = rs.getInt("UserId");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String userName = rs.getString("UserName");
        String email = rs.getString("Email");
        int password = rs.getInt("Password");
        int userRole = rs.getInt("UserRole");

        return new User(userId, firstName, lastName, userName, email, userRole, password);
    }

    /**
     * Creates and returns a User object from a ResultSet row.
     *
     * @param rs
     * @return User object
     * @throws SQLException
     */
    public Bug getBug(ResultSet rs) throws SQLException {

        // Get Bug info from ResultSet

        String description = rs.getString("Description");
        String dateRegistered = rs.getString("DateRegistered");
        int bugId = rs.getInt("Id");
        int resolved = rs.getInt("ResolvedStatus");
        int adminId = rs.getInt("UserId");
        int screenId = rs.getInt("ScreenId");

        return new Bug(bugId, description, dateRegistered, resolved, adminId, screenId);
    }
}
