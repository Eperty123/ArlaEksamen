package BE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Searcher {
    private static ObservableList<User> OLUsersReturn = FXCollections.observableArrayList();
    private static ObservableList<Bug> OLBugReturn = FXCollections.observableArrayList();
    private static ObservableList<Department> OLDepartmentReturn = FXCollections.observableArrayList();


    public static ObservableList<User> searchUsers(List<User> currentList, String query) {
        OLUsersReturn.clear();
        List<User> results = new ArrayList<>(currentList);
        results.removeIf(user -> !(user.getUserName().toLowerCase().contains(query.toLowerCase())
                || user.getFirstName().toLowerCase().contains(query.toLowerCase())
                || user.getLastName().toLowerCase().contains(query.toLowerCase())));

        OLUsersReturn.addAll(results);
        return OLUsersReturn;
    }

    public static ObservableList<Bug> searchBugs(List<Bug> currentList, String query) {
        OLBugReturn.clear();
        List<Bug> results = new ArrayList<>(currentList);
        results.removeIf(bug -> !(bug.getDateReported().toLowerCase().contains(query.toLowerCase())
                || bug.getDescription().toLowerCase().contains(query.toLowerCase())
                || bug.getFixMessage().toLowerCase().contains(query.toLowerCase())));


        OLBugReturn.addAll(results);
        return OLBugReturn;
    }

    public static ObservableList<Department> searchDepartments(ObservableList<Department> currentList, String query) {
        OLDepartmentReturn.clear();
        List<Department> results = new ArrayList<>(currentList);
        results.removeIf(department -> !(department.getName().toLowerCase().contains(query.toLowerCase())));


        OLDepartmentReturn.addAll(results);
        return OLDepartmentReturn;
    }
}

