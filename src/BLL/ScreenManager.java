package BLL;

import BE.ScreenBit;
import BE.User;
import DAL.ScreenDAL;

import java.util.List;

public class ScreenManager {

    ScreenDAL screenDAL = new ScreenDAL();

    /**
     * Adds a ScreenBit to the Database.
     * @param newScreenBit the desired ScreenBit to be added.
     */
    public void addScreenBit(ScreenBit newScreenBit){
        screenDAL.addScreenBit(newScreenBit);
    }

    /**
     * Deletes a ScreenBit from the Database.
     * @param screenBit the desired ScreenBit to be deleted.
     */
    public void deleteScreenBit(ScreenBit screenBit){
        screenDAL.deleteScreenBit(screenBit);
    }

    /**
     * Updates a ScreenBit in the Database.
     * @param newScreenBit the new ScreenBit.
     * @param oldScreenBit the old ScreenBit to be updated.
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit){
        screenDAL.updateScreenBit(newScreenBit, oldScreenBit);
    }

    /**
     * Gets a list of all ScreenBits in the Database.
     * @return a list of all ScreenBits in the Database.
     */
    public List<ScreenBit> getScreenBits(){
        return screenDAL.getScreenBits();
    }

    /**
     * Adds an association between a ScreenBit and a User.
     * @param user the User to be associated with.
     * @param screenBit the ScreenBit to be associated with.
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit){
        screenDAL.assignScreenBitRights(user, screenBit);
    }

    /**
     * Removes an association between a ScreenBit and a User.
     * @param user the User the association is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    public void removeScreenBitRights(User user, ScreenBit screenBit){
        screenDAL.removeScreenBitRights(user, screenBit);
    }

    /**
     * Removes associations between a list of Users and a ScreenBit.
     * @param users the list of users the associations is going to be removed from.
     * @param screenBit the ScreenBit the association is going to be removed from.
     */
    public void removeScreenBitRights(List<User> users, ScreenBit screenBit){
        screenDAL.removeScreenBitRights(users, screenBit);
    }
}
