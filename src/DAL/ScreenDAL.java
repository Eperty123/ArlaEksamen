package DAL;

import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();

    /**
     * Deletes a ScreenBit from the database.
     * @param screenBit used to get the ScreenBit's id, which is used to identify the row to be deleted.
     */
    public void deleteScreenBit(ScreenBit screenBit) {

        // First the ScreenBit is deleted from the ScreenRights junction table.
        deleteScreenBitUserAssociations(screenBit);

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Screen WHERE Id=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes all entries related to screenBit from the ScreenRights junction table.
     * @param screenBit object containing information on which rows to be deleted from the  ScreenRights table.
     */
    private void deleteScreenBitUserAssociations(ScreenBit screenBit) {
        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE ScreenId=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates ScreenName and ScreenInfo in the database.
     * @param newScreenBit contains the new ScreenBit information.
     * @param oldScreenBit used to get Id for row referencing.
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Screen SET ScreenName=?, ScreenInfo=? WHERE Id=?");
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.setInt(3, oldScreenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Creates a new ScreenBit in the database's Screen table. Id is assigned by the database automatically.
     * @param newScreenBit
     */
    public void addScreenBit(ScreenBit newScreenBit) {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Screen VALUES(?,?)");
            pSql.setString(1, newScreenBit.getName());
            pSql.setString(2, newScreenBit.getScreenInfo());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * This method joins row data from the Screen table, and the User table using the junction table ScreenRights.
     * ScreenBits are created, and assigned User objects are created and added to the screen which they are assigned to.
     * @return a list of all ScreenBit's with assigned Users.
     */
    public List<ScreenBit> getScreenBits(){
        List<ScreenBit> allScreens = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("" +
                    "SELECT " +
                    "Screen.Id AS ScreenId," +
                    "Screen.ScreenInfo," +
                    "Screen.ScreenName," +
                    "[User].Id AS UserId," +
                    "[User].FirstName," +
                    "[User].LastName," +
                    "[User].UserName," +
                    "[User].Email," +
                    "[User].Password," +
                    "[User].UserRole " +
                    "FROM Screen " +
                    "LEFT OUTER JOIN ScreenRights" +
                    "    ON Screen.Id = ScreenRights.ScreenId " +
                    "LEFT OUTER JOIN [User]" +
                    "    ON  ScreenId = ScreenRights.ScreenId AND [User].[UserName] = ScreenRights.UserName;");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()){

                ScreenBit newScreenBit = resultSetParser.getScreenBit(rs);
                User assignedUser = resultSetParser.getUser(rs);
                addScreenBitAndUser(allScreens, newScreenBit, assignedUser);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allScreens;
    }

    /**
     * Creates a row in the junction table ScreenRights in the database. An association in the ScreenRights table
     * consists of a ScreenId (int) and a UserName (String / NVARCHAR(10)).
     * @param user
     * @param screenBit
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenRights VALUES(?,?)");
            pSql.setInt(1, screenBit.getId());
            pSql.setString(2, user.getUserName());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes an association in the ScreenRights junction table in the database.
     * The row is identified using both userName and screenId.
     * @param user
     * @param screenBit
     */
    public void removeScreenBitRights(User user, ScreenBit screenBit){

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenRights WHERE UserName=? AND ScreenId=?");
            pSql.setString(1, user.getUserName());
            pSql.setInt(2, screenBit.getId());
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * * This helper method updates the allScreenBits list with data retrieved from the ResultSet in the getScreenBits() method.
     *
     * - If a user does not exist in allUsers, first the ScreenBit is assigned to the user,
     * and then the user is added to allUsers.
     * - If a user does exist in allUsers, the ScreenBit is added to the users list of assigned ScreenBits.
     * @param allScreens
     * @param newScreenBit
     * @param assignedUser
     */
    private void addScreenBitAndUser(List<ScreenBit> allScreens, ScreenBit newScreenBit, User assignedUser) {
        // If ScreenBit does not exist, it is added to the return list.
        if(allScreens.stream().noneMatch(o -> o.getId() == newScreenBit.getId())){
            if(assignedUser.getUserName() != null) newScreenBit.addUser(assignedUser);
            allScreens.add(newScreenBit);
        } else {
            // If ScreenBit does exist assignedUser is added to the ScreenBit
            for(ScreenBit s : allScreens){
                if(s.getId() == newScreenBit.getId() && assignedUser.getUserName() != null){
                    s.addUser(assignedUser);
                }
            }
        }
    }

}
