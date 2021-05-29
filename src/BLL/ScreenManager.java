package BLL;

import BE.IScreenCRUD;
import BE.ScreenBit;
import BE.User;
import DAL.ScreenDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class ScreenManager implements IScreenCRUD {

    private ObservableList<ScreenBit> screenBits = FXCollections.observableArrayList();
    private ScreenDAL screenDAL = new ScreenDAL();

    public ScreenManager() {
        initialize();
    }

    private void initialize() {
        screenBits.addAll(screenDAL.getScreenBits());
    }

    /**
     * Adds a ScreenBit to the Database.
     *
     * @param newScreenBit the desired ScreenBit to be added.
     */
    @Override
    public int addScreenBit(ScreenBit newScreenBit) throws SQLException {
        return screenDAL.addScreenBit(newScreenBit);
    }

    /**
     * Deletes a ScreenBit from the Database.
     *
     * @param screenBit the desired ScreenBit to be deleted.
     */
    @Override
    public void deleteScreenBit(ScreenBit screenBit) throws SQLException {
        screenDAL.deleteScreenBit(screenBit);
    }

    /**
     * Updates a ScreenBit in the Database.
     *
     * @param newScreenBit the new ScreenBit.
     * @param oldScreenBit the old ScreenBit to be updated.
     */
    @Override
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) throws SQLException {
        screenDAL.updateScreenBit(newScreenBit, oldScreenBit);
    }

    /**
     * Gets a list of all ScreenBits in the Database.
     *
     * @return a list of all ScreenBits in the Database.
     */
    @Override
    public List<ScreenBit> getAllScreenBits() {
        return screenDAL.getScreenBits();
    }

    /**
     * Adds an association between a ScreenBit and a User.
     *
     * @param user      the User to be associated with.
     * @param screenBit the ScreenBit to be associated with.
     */
    @Override
    public void assignScreenBitRights(User user, ScreenBit screenBit) throws SQLException {
        screenDAL.assignScreenBitRights(user, screenBit);
    }

    /**
     * Adds an association between a list of Users and a ScreenBit.
     *
     * @param users     The Users to associate screens with.
     * @param screenBit The ScreenBit to associate with the Users.
     * @throws SQLException
     */
    @Override
    public void assignScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException {
        screenDAL.assignScreenBitRights(users, screenBit);
    }

    /**
     * Removes an association between a ScreenBit and a User.
     *
     * @param user      the User the association is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    @Override
    public void removeScreenBitRights(User user, ScreenBit screenBit) throws SQLException {
        screenDAL.removeScreenBitRights(user, screenBit);
    }

    /**
     * Removes associations between a list of Users and a ScreenBit.
     *
     * @param users     the list of users the associations is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    @Override
    public void removeScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException {
    }
}
