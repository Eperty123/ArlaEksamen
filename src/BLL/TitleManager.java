package BLL;

import DAL.TitleDAL;

import java.util.List;

public class TitleManager {

    private final TitleDAL titleDAL = new TitleDAL();

    public void addTitle(String newTitle){
        this.titleDAL.addTitle(newTitle);
    }

    public void deleteTitle(String title){
        this.titleDAL.deleteTitle(title);
    }

    public List<String> getTitles(){
        return this.titleDAL.getTitles();
    }

    public void updateTitle(String oldTitle, String newTitle){
        this.titleDAL.updateTitle(oldTitle, newTitle);
    }



}
