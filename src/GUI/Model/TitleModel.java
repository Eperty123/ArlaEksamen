package GUI.Model;

import BE.ITitleCRUD;
import BLL.TitleManager;

import java.sql.SQLException;
import java.util.List;

public final class TitleModel implements ITitleCRUD {

    private static TitleModel instance;
    private final TitleManager titleManager = new TitleManager();

    @Override
    public void addTitle(String newTitle) throws SQLException {
        titleManager.addTitle(newTitle);
    }

    @Override
    public void deleteTitle(String title) throws SQLException {
        titleManager.deleteTitle(title);
    }

    /**
     * Updates the old title, to the new title in the database.
     *
     * @param oldTitle
     * @param newTitle
     * @throws SQLException
     */

    @Override
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleManager.updateTitle(oldTitle, newTitle);
    }

    /**
     * Returns a list of all titles.
     *
     * @return
     */
    @Override
    public List<String> getTitles() {
        return titleManager.getTitles();
    }

    @Override
    public boolean hasTitlesLoaded() {
        return titleManager.hasTitlesLoaded();
    }


    public static TitleModel getInstance() {
        return instance == null ? instance = new TitleModel() : instance;
    }

}
