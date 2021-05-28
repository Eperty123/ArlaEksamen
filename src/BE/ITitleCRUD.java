package BE;

import java.sql.SQLException;
import java.util.List;

public interface ITitleCRUD {

    void addTitle(String newTitle) throws SQLException;

    void deleteTitle(String title) throws SQLException;

    List<String> getTitles();

    void updateTitle(String oldTitle, String newTitle) throws SQLException;

    boolean hasTitlesLoaded();
}
