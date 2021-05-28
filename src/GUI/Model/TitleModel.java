package GUI.Model;

import BLL.TitleManager;

import java.sql.SQLException;
import java.util.List;

public final class TitleModel {

    private static TitleModel instance;
    private final TitleManager titleManager;


    public static TitleModel getInstance(){
        return instance == null ? instance = new TitleModel() : instance;
    }

    private TitleModel(){
        titleManager = new TitleManager();
    }

    /**
     * Adds a title to the database.
     * @param newTitle
     */
    public void addTitle(String newTitle){
        titleManager.addTitle(newTitle);
    }

    /**
     * Deletes a title in the database.
     * @param title
     * @throws SQLException
     */
    public void deleteTitle(String title) throws SQLException {
        titleManager.deleteTitle(title);
    }

    /**
     * Returns a list of all titles.
     * @return
     */
    public List<String> getTitles(){
        return titleManager.getTitles();
    }

    /**
     * Updates the old title, to the new title in the database.
     * @param oldTitle
     * @param newTitle
     * @throws SQLException
     */
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleManager.updateTitle(oldTitle, newTitle);
    }

}
