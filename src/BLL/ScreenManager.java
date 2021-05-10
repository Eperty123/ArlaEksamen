package BLL;

import BE.ScreenBit;
import BE.User;
import DAL.ScreenDAL;

import java.util.List;

public class ScreenManager {

    ScreenDAL screenDAL = new ScreenDAL();

    public void addScreenBit(ScreenBit newScreenBit){
        screenDAL.addScreenBit(newScreenBit);
    }

    public void deleteScreenBit(ScreenBit screenBit){
        screenDAL.deleteScreenBit(screenBit);
    }

    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit){
        screenDAL.updateScreenBit(newScreenBit, oldScreenBit);
    }

    public List<ScreenBit> getScreenBits(){
        return screenDAL.getScreenBits();
    }

    public void assignScreenBitRights(User user, ScreenBit screenBit){
        screenDAL.assignScreenBitRights(user, screenBit);
    }

    public void removeScreenBitRights(User user, ScreenBit screenBit){
        screenDAL.removeScreenBitRights(user, screenBit);
    }

}
