package BLL;

import BE.Screen;
import BE.User;
import DAL.ScreenDAL;

import java.util.List;

public class ScreenManager {

    ScreenDAL screenDAL = new ScreenDAL();

    public void addScreen(Screen newScreen){
        screenDAL.addScreen(newScreen);
    }

    public void deleteScreen(Screen screen){
        screenDAL.deleteScreen(screen);
    }

    public void updateScreen(Screen newScreen, Screen oldScreen){
        screenDAL.updateScreen(newScreen, oldScreen);
    }

    public List<Screen> getScreens(){
        return screenDAL.getScreens();
    }

    public void assignScreenRights(User user, Screen screen){
        screenDAL.assignScreenRights(user, screen);
    }

    public void removeScreenRights(User user, Screen screen){
        screenDAL.removeScreenRights(user, screen);
    }

}
