package BLL;

import BE.ITitleCRUD;
import DAL.TitleDAL;

import java.sql.SQLException;
import java.util.List;

public class TitleManager implements ITitleCRUD {

    private final TitleDAL titleDAL = new TitleDAL();
    
    /**
     * Add the specified title to the database.
     *
     * @param newTitle The title to add.
     * @throws SQLException
     */
    @Override
    public void addTitle(String newTitle) throws SQLException {
        titleDAL.addTitle(newTitle);
    }

    /**
     * Delete the title from the database.
     *
     * @param title The title to delete.
     * @throws SQLException
     */
    @Override
    public void deleteTitle(String title) throws SQLException {
        titleDAL.deleteTitle(title);
    }

    /**
     * Get a list of titles.
     *
     * @return Returns a list of titles.
     */
    @Override
    public List<String> getTitles() {
        return titleDAL.getTitles();
    }

    /**
     * Update a title with a new one.
     *
     * @param oldTitle The current title to update.
     * @param newTitle The new title to update to.
     * @throws SQLException
     */
    @Override
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleDAL.updateTitle(oldTitle, newTitle);
    }
}
