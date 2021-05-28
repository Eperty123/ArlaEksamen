package GUI.Model;

import BE.ScreenBit;
import BE.User;
import BLL.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public final class ScreenModel {

    private final ScreenManager screenManager;
    private static ScreenModel instance;



    private ScreenModel() {
        screenManager = new ScreenManager();
    }

    /**
     * Returns an instance of the Singleton ScreenManager
     *
     * @return ScreenManager
     */
    public static ScreenModel getInstance() {
        return instance == null ? instance = new ScreenModel() : instance;
    }

    /**
     * Adds a new ScreenBit to the database.
     *
     * @param newScreenBit object containing info on the new ScreenBit
     */
    public int addScreenBit(ScreenBit newScreenBit) {
        // Not sure if this still return false with ! in it.
          return screenManager.addScreenBit(newScreenBit);
    }

    /**
     * Deletes the specified ScreenBit from the database.
     *
     * @param screenBit object containing information to identify the row in the database.
     */
    public void deleteScreenBit(ScreenBit screenBit) throws SQLException {
        screenManager.deleteScreenBit(screenBit);

    }

    /**
     * @return A list of all ScreenBit objects
     */
    public List<ScreenBit> getAllScreenBits() {
        return screenManager.getScreenBits();
    }

    /**
     * This method passes two ScreenBit objects to the ScreenModel, and on to ScreenDAL,
     * so the associated row (oldScreenBit) will be updated with the new information (newScreenBit)
     *
     * @param newScreenBit object containing the new ScreenBit information
     * @param oldScreenBit object used to identify the existing row in the database.
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) {
        screenManager.updateScreenBit(newScreenBit, oldScreenBit);

    }

    /**
     * This method calls ScreenManager, and through that ScreenDal to assign screen rights
     * to a user. After this, the updateScreen() method re-initializes the allScreenBits list.
     *
     * @param user      object used to identify the user to be assigned rights in the database.
     * @param screenBit object used to identify the screen to be assigned to the user.
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit) {
        screenManager.assignScreenBitRights(user, screenBit);
    }

    /**
     * Creates an association between users and ScreenBit in the database.
     * @param users
     * @param screenBit
     */
    public void assignScreenBitRights(List<User> users, ScreenBit screenBit) {
        screenManager.assignScreenBitRights(users, screenBit);
    }

    /**
     * Removes the association between users and ScreenBit in the database.
     * @param user
     * @param screenBit
     */
    public void removeScreenBitRights(User user, ScreenBit screenBit) {
        screenManager.removeScreenBitRights(user, screenBit);

    }

    /**
     * Removes the association between users and ScreenBit in the database.
     * @param users
     * @param screenBit
     */
    public void removeScreenBitRights(List<User> users, ScreenBit screenBit) {
        screenManager.removeScreenBitRights(users, screenBit);

    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }


}
