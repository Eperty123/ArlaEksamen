package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;
import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();

    /**
     * Deletes a ScreenBit from the database.
     *
     * @param screenBit used to get the ScreenBit's id, which is used to identify the row to be deleted.
     */
    public void deleteScreenBit(ScreenBit screenBit) {

        // First the ScreenBit is deleted from the ScreenRights junction table.
        try (Connection con = dbCon.getConnection()) {
            deleteScreenBitUserAssociations(con, screenBit);
            deleteScreenBitTimeTable(con,screenBit);
            deleteScreenBitMessage(con,screenBit);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Screen WHERE Id=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete the given screen " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    private void deleteScreenBitMessage(Connection con, ScreenBit screenBit) {

        try {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenMessage WHERE ScreenId=?");
            pSql.setInt(1, screenBit.getId());
        }catch (SQLException throwables){
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a message on the given screen " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }

    private void deleteScreenBitTimeTable(Connection con, ScreenBit screenBit) {

        try {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenTime WHERE ScreenId=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a timetable on the given screen " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Deletes all entries related to screenBit from the ScreenRights junction table.
     *
     * @param screenBit object containing information on which rows to be deleted from the  ScreenRights table.
     */
    private void deleteScreenBitUserAssociations(Connection con, ScreenBit screenBit) {
        try{
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE ScreenId=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a user - screen association" +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Updates ScreenName and ScreenInfo in the database.
     *
     * @param newScreenBit contains the new ScreenBit information.
     * @param oldScreenBit used to get Id for row referencing.
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Screen SET ScreenName=?, ScreenInfo=? WHERE Id=?");
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.setInt(3, oldScreenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update the given screen " +
                "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Creates a new ScreenBit in the database's Screen table. Id is assigned by the database automatically.
     *
     * @param newScreenBit
     */
    public void addScreenBit(ScreenBit newScreenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Screen VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.executeUpdate();

            ResultSet generatedKeys = pSql.getGeneratedKeys();
            generatedKeys.next();

            createScreenBitTimeTable(con, generatedKeys.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add the given screen " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    // TODO javadoc jonas
    private void createScreenBitTimeTable(Connection con, int screenId) {

        try {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenTime VALUES(?,?,?)");

            // Creates 48 time slots of 30 minutes pr. day, one year forward from current date.
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 48; j++) {
                    pSql.setInt(1, screenId);
                    pSql.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(i), LocalTime.ofSecondOfDay(j * 1800))));
                    pSql.setBoolean(3, true);
                    pSql.addBatch();
                }
            }
            pSql.executeBatch();

            pSql.execute();
        }catch (SQLException throwables){
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a timetable the given screen " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }


    /**
     * This method joins row data from the Screen table, and the User table using the junction table ScreenRights.
     * ScreenBits are created, and assigned User objects are created and added to the screen which they are assigned to.
     *
     * @return a list of all ScreenBit's with assigned Users.
     */
    public List<ScreenBit> getScreenBits() {
        List<ScreenBit> allScreens = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("" +
                    "SELECT " +
                    "Screen.Id AS ScreenId," +
                    "Screen.ScreenInfo," +
                    "Screen.ScreenName," +
                    "[User].* " +
                    "FROM Screen " +
                    "LEFT OUTER JOIN ScreenRights" +
                    "    ON Screen.Id = ScreenRights.ScreenId " +
                    "LEFT OUTER JOIN [User]" +
                    "    ON  ScreenId = ScreenRights.ScreenId AND [User].[UserName] = ScreenRights.UserName;");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {

                ScreenBit newScreenBit = resultSetParser.getScreenBit(rs);
                User assignedUser = resultSetParser.getUser(rs);
                addScreenBitAndUser(allScreens, newScreenBit, assignedUser);
            }
            loadTimeTables(con, allScreens);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all screens from " +
                    "the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return allScreens;
    }

    /**
     * Creates a row in the junction table ScreenRights in the database. An association in the ScreenRights table
     * consists of a ScreenId (int) and a UserName (String / NVARCHAR(10)).
     *
     * @param user
     * @param screenBit
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenRights VALUES(?,?)");
            pSql.setInt(1, screenBit.getId());
            pSql.setString(2, user.getUserName());
            pSql.execute();
            System.out.println("ASSIGN EXECUTED");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to assign screen rights between " +
                    "the given user and screen. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Deletes an association in the ScreenRights junction table in the database.
     * The row is identified using both userName and screenId.
     *
     * @param user
     * @param screenBit
     */
    public void removeScreenBitRights(User user, ScreenBit screenBit) {
        System.out.println("removeScreenBitRights called");
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=? AND ScreenId=?");
            pSql.setString(1, user.getUserName());
            pSql.setInt(2, screenBit.getId());
            pSql.execute();
            System.out.println("EXECUTED");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to remove screen rights between " +
                    "the given user and screen. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * Deletes an association in the ScreenRights junction table in the database using a batch query.
     * The row is identified using both userName and screenId.
     * @param users
     * @param screenBit
     */
    public void removeScreenBitRights(List<User> users, ScreenBit screenBit){
        System.out.println(users.size());

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=? AND ScreenId=?");

            for(User u : users){
                pSql.setString(1, u.getUserName());
                pSql.setInt(2, screenBit.getId());
                pSql.executeBatch();
            }

            pSql.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to remove screen rights between " +
                    "the given list of users and screen. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    /**
     * * This helper method updates the allScreenBits list with data retrieved from the ResultSet in the getScreenBits() method.
     * <p>
     * - If a user does not exist in allUsers, first the ScreenBit is assigned to the user,
     * and then the user is added to allUsers.
     * - If a user does exist in allUsers, the ScreenBit is added to the users list of assigned ScreenBits.
     *
     * @param allScreens
     * @param newScreenBit
     * @param assignedUser
     */
    private void addScreenBitAndUser(List<ScreenBit> allScreens, ScreenBit newScreenBit, User assignedUser) {
        // If ScreenBit does not exist, it is added to the return list.
        if (allScreens.stream().noneMatch(o -> o.getId() == newScreenBit.getId())) {
            if (assignedUser.getUserName() != null) newScreenBit.addUser(assignedUser);
            allScreens.add(newScreenBit);
        } else {
            // If ScreenBit does exist assignedUser is added to the ScreenBit
            for (ScreenBit s : allScreens) {
                if (s.getId() == newScreenBit.getId() && assignedUser.getUserName() != null) {
                    s.addUser(assignedUser);
                }
            }
        }
    }

    // TODO javadoc jonas
    private void loadTimeTables(Connection con, List<ScreenBit> screenBits) {

        try {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM ScreenTime");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {
                int screenId = rs.getInt("ScreenId");
                LocalDateTime timeSlot = rs.getTimestamp("TimeSlot").toLocalDateTime();
                boolean available = rs.getBoolean("Available");

                for (ScreenBit s : screenBits) {
                    if (s.getId() == screenId) {
                        s.getTimeTable().put(timeSlot, available);
                    }
                }
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to load timetables from the Database. " +
                    "Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

}
