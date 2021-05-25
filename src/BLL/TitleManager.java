package BLL;

import DAL.TitleDAL;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class TitleManager {

    private TitleDAL titleDAL = new TitleDAL();

    public void addTitle(String newTitle){
        titleDAL.addTitle(newTitle);
    }

    public void deleteTitle(String title){
        titleDAL.deleteTitle(title);
    }

    public List<String> getTitles(){
        return titleDAL.getTitles();
    }

    public void updateTitle(String oldTitle, String newTitle){
        titleDAL.updateTitle(oldTitle, newTitle);
    }



}
