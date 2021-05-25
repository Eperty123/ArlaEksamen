package GUI.Model;

import BLL.TitleManager;
import DAL.TitleDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TitleModel {

    private static TitleModel instance;
    private TitleManager titleManager;
    private ObservableList<String> allTitles;

    public static TitleModel getInstance(){
        return instance == null ? instance = new TitleModel() : instance;
    }

    private TitleModel(){
        allTitles = FXCollections.observableArrayList();
        titleManager = new TitleManager();
        allTitles.addAll(getTitles());
    }

    public void addTitle(String newTitle){
        titleManager.addTitle(newTitle);
        if(!allTitles.contains(newTitle)){
            allTitles.add(newTitle);
        }

    }

    public void deleteTitle(String title){
        titleManager.deleteTitle(title);
        allTitles.remove(title);
    }

    public List<String> getTitles(){
        return titleManager.getTitles();
    }

    public void updateTitle(String oldTitle, String newTitle){
        titleManager.updateTitle(oldTitle, newTitle);
        allTitles.remove(oldTitle);
        allTitles.add(newTitle);
    }

}
