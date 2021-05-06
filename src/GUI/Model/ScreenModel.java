package GUI.Model;

import BE.Screen;
import BE.User;
import BLL.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScreenModel {

    private ScreenManager screenManager;
    private static ScreenModel instance;

    private ObservableList<Screen> allScreens;

    public ScreenModel(){
        screenManager = new ScreenManager();
        allScreens = FXCollections.observableArrayList();
        initialize();
    }

    private void initialize() {
        allScreens.addAll(loadScreensAndAssignedUser());
    }

    public static ScreenModel getInstance() {
        return instance == null ? instance = new ScreenModel() : instance;
    }

    public void addScreen(Screen newScreen){
        screenManager.addScreen(newScreen);
        allScreens.add(newScreen);
    }

    public void deleteScreen(Screen screen){
        screenManager.deleteScreen(screen);
        allScreens.remove(screen);
    }

    public ObservableList<Screen> getAllScreens() {
        return allScreens;
    }

    public void updateScreen(Screen newScreen, Screen oldScreen){
        screenManager.updateScreen(newScreen, oldScreen);
        allScreens.remove(oldScreen);
        allScreens.add(newScreen);
    }

    public void assignScreenRights(User user, Screen screen){
        screenManager.assignScreenRights(user, screen);

    }

    public void removeScreenRights(User user, Screen screen){
        screenManager.removeScreenRights(user, screen);
    }

    public void updateAllScreensAssignRights(User user, Screen screen){
        for(Screen s : allScreens){
            if(s.getId() == screen.getId()){
                s.addUser(user);
            }
        }
    }

    public void updateAllScreensDeleteRights(User user, Screen screen){
        for(Screen s : allScreens){
            if(s.getId() == screen.getId()){
                s.removeUser(user);
            }
        }
    }

    public List<Screen> loadScreensAndAssignedUser(){
        HashMap<Screen, String> screenUserHashMap = screenManager.getScreens();
        List<Screen> allScreens = new ArrayList<>();

        List<User> users = UserModel.getInstance().getAllUsers();

        screenUserHashMap.forEach((k, v) -> {
            k.addUser(getUserByName(users, v));
            addScreenToUser(k, v);
            allScreens.add(k);
        });

        return allScreens;
    }

    public User getUserByName(List<User> users, String userName){
        for (User u : users){
            if(u.getUserName().equals(userName)) return u;
        }
        return null;
    }

    public void addScreenToUser(Screen screen, String userName){
        UserModel.getInstance().assignScreenByUserName(screen, userName);
    }



}
