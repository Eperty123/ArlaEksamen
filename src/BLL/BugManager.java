package BLL;

import BE.Bug;
import DAL.BugDAL;

import java.sql.SQLException;
import java.util.List;

public class BugManager {

    BugDAL bugDAL = new BugDAL();

    public List<Bug> getBugs() throws SQLException {
        return bugDAL.getAllBugs();
    }

    public void addBug(Bug bug) {
        bugDAL.addBug(bug);
    }

    public void updateBug(Bug oldBug, Bug updatedBug) {
        bugDAL.updateBug(oldBug, updatedBug);
    }

    public void deleteBug(Bug bug) {
        bugDAL.deleteBug(bug);
    }
}
