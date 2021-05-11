package GUI.Model;

import BE.ScreenBit;
import BE.User;
import BLL.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ScreenModel {

    private ScreenManager screenManager;
    private static ScreenModel instance;

    private ObservableList<ScreenBit> allScreenBits;

    public ScreenModel() {
        screenManager = new ScreenManager();
        allScreenBits = FXCollections.observableArrayList();
        initialize();
    }

    /**
     * Initializing the allScreenBits list with a database query through ScreenManager
     */
    private void initialize() {
        allScreenBits.addAll(screenManager.getScreenBits());
    }

    /**
     * Returns an instance of the Singleton ScreenManager
     * @return ScreenManager
     */
    public static ScreenModel getInstance() {
        return instance == null ? instance = new ScreenModel() : instance;
    }

    /**
     * If a ScreenBit does not already exist, with the same name as newScreenBit, a new row will be created
     * in the database.
     * @param newScreenBit object containing info on the new ScreenBit
     */
    public void addScreenBit(ScreenBit newScreenBit){
        if(allScreenBits.stream().noneMatch(o ->o.getName().equals(newScreenBit.getName()))){
            screenManager.addScreenBit(newScreenBit);
            updateScreenBits();
        }
        // TODO make GUI handle error ?

    }

    /**
     * Method used to re-initialize the allScreenBits list after CRUD operations.
     */
    public void updateScreenBits() {
        allScreenBits.clear();
        allScreenBits.addAll(screenManager.getScreenBits());
    }

    /**
     * Deletes the specified ScreenBit from the database.
     * @param screenBit object containing information to identify the row in the database.
     */
    public void deleteScreenBit(ScreenBit screenBit){
        screenManager.deleteScreenBit(screenBit);
        updateScreenBits();
    }

    /**
     * @return A list of all ScreenBit objects
     */
    public ObservableList<ScreenBit> getAllScreenBits() {
        return allScreenBits;
    }

    /**
     * This method passes two ScreenBit objects to the ScreenModel, and on to ScreenDAL,
     * so the associated row (oldScreenBit) will be updated with the new information (newScreenBit)
     * @param newScreenBit object containing the new ScreenBit information
     * @param oldScreenBit object used to identify the existing row in the database.
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit){
        screenManager.updateScreenBit(newScreenBit, oldScreenBit);
        updateScreenBits();
    }

    /**
     * This method calls ScreenManager, and throuhg that ScreenDal to assign screen rights
     * to a user. After this, the updateScreen() method re-initializes the allScreenBits list.
     * @param user object used to identify the user to be assigned rights in the database.
     * @param screenBit object used to identify the screen to be assigned to the user.
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit){
        screenManager.assignScreenBitRights(user, screenBit);
        updateScreenBits();
    }

    public void removeScreenBitRights(User user, ScreenBit screenBit){
        screenManager.removeScreenBitRights(user,screenBit);
        updateScreenBits();
    }

    public void removeScreenBitRights(List<User> users, ScreenBit screenBit){
        screenManager.removeScreenBitRights(users,screenBit);
        updateScreenBits();
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
            System.out.println(String.format("%s singleton was reset.", getClass().getSimpleName()));
        }
    }
}
