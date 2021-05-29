package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IBugCRUD {
    /**
     * Gets a list of all the bugs from the database.
     *
     * @return a list of bugs from the database.
     */
    ObservableList<Bug> getAllBugs();

    /**
     * Adds a bug to the Database.
     *
     * @param bug the given bug to add.
     */
    void addBug(Bug bug) throws SQLException;

    /**
     * Updates a bug in the database.
     *
     * @param oldBug     the old bug to be updated.
     * @param updatedBug the updated bug.
     */
    void updateBug(Bug oldBug, Bug updatedBug) throws SQLException;

    /**
     * Deletes a bug from the database.
     *
     * @param bug the bug to be deleted.
     */
    void deleteBug(Bug bug) throws SQLException;
}
