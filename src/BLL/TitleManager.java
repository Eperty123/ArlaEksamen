package BLL;

import BE.ITitleCRUD;
import DAL.TitleDAL;

import java.sql.SQLException;
import java.util.List;

public class TitleManager implements ITitleCRUD {

    private final TitleDAL titleDAL = new TitleDAL();
    private boolean titlesLoaded;

    public TitleManager() {
        initialize();
    }

    private void initialize() {
        titlesLoaded = titleDAL.getTitles() != null;
    }

    @Override
    public void addTitle(String newTitle) throws SQLException {
        titleDAL.addTitle(newTitle);
    }

    @Override
    public void deleteTitle(String title) throws SQLException {
        titleDAL.deleteTitle(title);
    }

    @Override
    public List<String> getTitles() {
        return titleDAL.getTitles();
    }

    @Override
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleDAL.updateTitle(oldTitle, newTitle);
    }

    @Override
    public boolean hasTitlesLoaded() {
        return titlesLoaded;
    }


}
