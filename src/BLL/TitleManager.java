package BLL;

import DAL.TitleDAL;

import java.sql.SQLException;
import java.util.List;

public class TitleManager {

    private final TitleDAL titleDAL = new TitleDAL();

    /**
     * Adds a title to the database.
     * @param newTitle
     */
    public void addTitle(String newTitle){
        this.titleDAL.addTitle(newTitle);
    }

    /**
     * Deletes a title from the database.
     * @param title
     * @throws SQLException
     */
    public void deleteTitle(String title) throws SQLException {
        this.titleDAL.deleteTitle(title);
    }

    /**
     * Retrieves all titles stored in the database.
     * @return
     */
    public List<String> getTitles(){
        return this.titleDAL.getTitles();
    }

    /**
     * Updates a title in the database.
     * @param oldTitle
     * @param newTitle
     * @throws SQLException
     */
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        this.titleDAL.updateTitle(oldTitle, newTitle);
    }



}
