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

    public void addTitle(String newTitle){
        titleManager.addTitle(newTitle);
    }

    public void deleteTitle(String title){
        titleManager.deleteTitle(title);
    }

    public List<String> getTitles(){
        return titleManager.getTitles();
    }

    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleManager.updateTitle(oldTitle, newTitle);
    }

}
