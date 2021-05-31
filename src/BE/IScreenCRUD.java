package BE;

import java.sql.SQLException;
import java.util.List;

public interface IScreenCRUD {

    /**
     * Adds a ScreenBit to the Database.
     *
     * @param newScreenBit the desired ScreenBit to be added.
     * @return
     */
    int addScreenBit(ScreenBit newScreenBit) throws SQLException;

    /**
     * Deletes a ScreenBit from the Database.
     *
     * @param screenBit the desired ScreenBit to be deleted.
     */
    void deleteScreenBit(ScreenBit screenBit) throws SQLException;

    /**
     * Updates a ScreenBit in the Database.
     *
     * @param newScreenBit the new ScreenBit.
     * @param oldScreenBit the old ScreenBit to be updated.
     */
    void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) throws SQLException;

    /**
     * Gets a list of all ScreenBits in the Database.
     *
     * @return a list of all ScreenBits in the Database.
     */
    List<ScreenBit> getAllScreenBits();

    /**
     * Adds an association between a ScreenBit and a User.
     *
     * @param user      the User to be associated with.
     * @param screenBit the ScreenBit to be associated with.
     */
    void assignScreenBitRights(User user, ScreenBit screenBit) throws SQLException;

    /**
     * Assign a list of Users with a list of ScreenBits.
     *
     * @param users     The users to associate screens with.
     * @param screenBit The screens to associate with users.
     * @throws SQLException
     */
    void assignScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException;

    /**
     * Removes an association between a ScreenBit and a User.
     *
     * @param user      the User the association is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    void removeScreenBitRights(User user, ScreenBit screenBit) throws SQLException;

    /**
     * Removes associations between a list of Users and a ScreenBit.
     *
     * @param users     the list of users the associations is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    void removeScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException;
}
