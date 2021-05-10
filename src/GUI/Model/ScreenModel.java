package GUI.Model;

import BE.ScreenBit;
import BE.User;
import BLL.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
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

    private void initialize() {
        allScreenBits.addAll(loadScreensAndAssignedUser());
    }

    public static ScreenModel getInstance() {
        return instance == null ? instance = new ScreenModel() : instance;
    }

    public void addScreen(ScreenBit newScreenBit) {
        screenManager.addScreen(newScreenBit);
        allScreenBits.add(newScreenBit);
    }

    public void deleteScreen(ScreenBit screenBit) {
        screenManager.deleteScreen(screenBit);
        allScreenBits.remove(screenBit);
    }

    public ObservableList<ScreenBit> getAllScreens() {
        return allScreenBits;
    }

    public void updateScreen(ScreenBit newScreenBit, ScreenBit oldScreenBit) {

        // Update the screen in the database.
        screenManager.updateScreen(newScreenBit, oldScreenBit);

        // If for some reason the screen object is unable to get deleted from the list, let's not do anything,
        // but instead let the current updated one stay until the entire list gets refreshed by a database call.
        if (allScreenBits.remove(oldScreenBit)) {
            allScreenBits.add(newScreenBit);
            System.out.println(String.format("Successfully remove screen bit: %s.", oldScreenBit.getName()));
        } else System.out.println(String.format("Failed to remove screen bit: %s. Fucked reference, maybe?", oldScreenBit.getName()));
    }

    public void assignScreenRights(User user, ScreenBit screenBit) {
        screenManager.assignScreenRights(user, screenBit);
    }

    public void removeScreenRights(User user, ScreenBit screenBit) {
        screenManager.removeScreenRights(user, screenBit);
    }

    public void updateAllScreensAssignRights(User user, ScreenBit screenBit) {
        for (ScreenBit s : allScreenBits) {
            if (s.getId() == screenBit.getId()) {
                s.addUser(user);
            }
        }
    }

    public void updateAllScreensDeleteRights(User user, ScreenBit screenBit) {
        for (ScreenBit s : allScreenBits) {
            if (s.getId() == screenBit.getId()) {
                s.removeUser(user);
            }
        }
    }

    public List<ScreenBit> loadScreensAndAssignedUser() {
        HashMap<ScreenBit, String> screenUserHashMap = screenManager.getScreens();
        List<ScreenBit> allScreenBits = new ArrayList<>();

        List<User> users = UserModel.getInstance().getAllUsers();

        screenUserHashMap.forEach((k, v) -> {
            k.addUser(getUserByName(users, v));
            addScreenToUser(k, v);
            allScreenBits.add(k);
        });

        return allScreenBits;
    }

    public User getUserByName(List<User> users, String userName) {
        for (User u : users) {
            if (u.getUserName().equals(userName)) return u;
        }
        return null;
    }

    public void addScreenToUser(ScreenBit screenBit, String userName) {
        UserModel.getInstance().assignScreenByUserName(screenBit, userName);
    }

    public ScreenBit getScreenBitByName(String ScreenBitName) {
        for (ScreenBit s : allScreenBits) {
            if (s.getName().equals(ScreenBitName)) return s;
        }
        return null;
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
