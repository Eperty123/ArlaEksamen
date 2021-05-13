package BLL;

import BE.Bug;

import java.sql.SQLException;
import java.util.List;

public class BugManager {

    //BugDAL bugDAL = new BugDAL();

    public List<Bug> getBugs() throws SQLException {
        //return bugDAL.getBugs();
        return null;
    }

    public void addBug(Bug bug){
        //bugDAL.addBug(bug);
    }

    public void updateBug(Bug bug, Bug updatedBug){
        //bugDAL.updateBug(bug, updatedBug);
    }

    public void deleteBug(Bug bug) {
        //bugDAL.deleteBug(bug);
    }
}
