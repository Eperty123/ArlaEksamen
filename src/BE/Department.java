package BE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {
    private int id;
    private String name;
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Department> subDepartments = FXCollections.observableArrayList();

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(int id, String name, ObservableList<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Department(int id, String name, ObservableList<User> users, ObservableList<Department> subDepartments) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.subDepartments = subDepartments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<Department> getSubDepartments() {
        return subDepartments;
    }
}
