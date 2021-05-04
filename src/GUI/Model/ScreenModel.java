package GUI.Model;

import BE.Screen;
import BE.User;
import BLL.ScreenManager;

import java.util.List;

public class ScreenModel {

    private ScreenManager screenManager = new ScreenManager();

    private List<Screen> allScreens;

    public ScreenModel(){
        initialize();
    }

    private void initialize() {
        this.allScreens = screenManager.getScreens();
    }

    public void addScreen(Screen newScreen){
        screenManager.addScreen(newScreen);
        allScreens.add(newScreen);
    }

    public void deleteScreen(Screen screen){
        screenManager.deleteScreen(screen);
        allScreens.remove(screen);
    }

    public List<Screen> getAllScreens() {
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


}
