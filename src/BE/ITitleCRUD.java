package BE;

import java.sql.SQLException;
import java.util.List;

public interface ITitleCRUD {

    /**
     * Add the specified title to the database.
     *
     * @param newTitle The title to add.
     * @throws SQLException
     */
    void addTitle(String newTitle) throws SQLException;

    /**
     * Delete the title from the database.
     *
     * @param title The title to delete.
     * @throws SQLException
     */
    void deleteTitle(String title) throws SQLException;

    /**
     * Get a list of titles.
     *
     * @return Returns a list of titles.
     */
    List<String> getTitles();

    /**
     * Update a title with a new one.
     *
     * @param oldTitle The current title to update.
     * @param newTitle The new title to update to.
     * @throws SQLException
     */
    void updateTitle(String oldTitle, String newTitle) throws SQLException;
}
