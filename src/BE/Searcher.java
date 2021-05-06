package BE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Searcher {
    private static ObservableList<User> OLreturn = FXCollections.observableArrayList();


    public static ObservableList<User> search(List<User> currentList, String query) {
        OLreturn.clear();
        List<User> results = new ArrayList<>(currentList);
        results.removeIf(user -> !(user.getUserName().toLowerCase().contains(query.toLowerCase())
                                || user.getFirstName().toLowerCase().contains(query.toLowerCase())
                                || user.getLastName().toLowerCase().contains(query.toLowerCase())));

        OLreturn.addAll(results);
        return OLreturn;
    }
}

