package BLL;

import BE.ScreenBit;
import BE.User;
import DAL.ScreenDAL;

import java.util.HashMap;
import java.util.List;

public class ScreenManager {

    ScreenDAL screenDAL = new ScreenDAL();

    public void addScreen(ScreenBit newScreenBit){
        screenDAL.addScreen(newScreenBit);
    }

    public void deleteScreen(ScreenBit screenBit){
        screenDAL.deleteScreen(screenBit);
    }

    public void updateScreen(ScreenBit newScreenBit, ScreenBit oldScreenBit){
        screenDAL.updateScreen(newScreenBit, oldScreenBit);
    }

    public List<ScreenBit> getScreens(){
        return screenDAL.getScreens();
    }

    public void assignScreenRights(User user, ScreenBit screenBit){
        screenDAL.assignScreenRights(user, screenBit);
    }

    public void removeScreenRights(User user, ScreenBit screenBit){
        screenDAL.removeScreenRights(user, screenBit);
    }

}
