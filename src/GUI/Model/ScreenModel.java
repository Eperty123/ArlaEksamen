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
        allScreenBits.addAll(screenManager.getScreens());
    }

    public static ScreenModel getInstance() {
        return instance == null ? instance = new ScreenModel() : instance;
    }

    public void addScreen(ScreenBit newScreenBit){
        screenManager.addScreen(newScreenBit);
        updateScreenBits();
    }

    public void updateScreenBits() {
        allScreenBits.clear();
        allScreenBits.addAll(screenManager.getScreens());
    }

    public void deleteScreen(ScreenBit screenBit){
        screenManager.deleteScreen(screenBit);
        updateScreenBits();
    }

    public ObservableList<ScreenBit> getAllScreens() {
        return allScreenBits;
    }

    public void updateScreen(ScreenBit newScreenBit, ScreenBit oldScreenBit){
        screenManager.updateScreen(newScreenBit, oldScreenBit);
        allScreenBits.remove(oldScreenBit);
        allScreenBits.add(newScreenBit);
    }

    public void assignScreenRights(User user, ScreenBit screenBit){
        screenManager.assignScreenRights(user, screenBit);
        updateScreenBits();
    }

    public void removeScreenRights(User user, ScreenBit screenBit){
        screenManager.removeScreenRights(user, screenBit);
    }


    public User getUserByName(List<User> users, String userName) {
        for (User u : users) {
            if (u.getUserName().equals(userName)) return u;
        }
        return null;
    }


    public ScreenBit getScreenBitByName(String ScreenBitName){
        for(ScreenBit s : allScreenBits){
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
