package BE;

import GUI.Controller.DPT.DepartmentViewController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(-1);
    private StringProperty name = new SimpleStringProperty("");
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Department> subDepartments = FXCollections.observableArrayList();
    private BooleanProperty isSubDepartment = new SimpleBooleanProperty(false);
    private DepartmentViewController departmentViewController;

    public DepartmentViewController getDepartmentViewController() {
        return departmentViewController;
    }

    public void setDepartmentViewController(DepartmentViewController departmentViewController) {
        this.departmentViewController = departmentViewController;
    }

    public Department(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    public Department(int id, String name, ObservableList<User> users) {
        this.id.set(id);
        this.name.set(name);
        this.users = users;
    }

    public Department(int id, String name, ObservableList<User> users, ObservableList<Department> subDepartments) {
        this.id.set(id);
        this.name.set(name);
        this.users = users;
        this.subDepartments = subDepartments;
    }

    public int getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<Department> getSubDepartments() {
        return subDepartments;
    }

    public void addSubDepartment(Department department){

    }

    public ObservableList<Department> getAllSubDepartments() {
        ObservableList<Department> tmp = FXCollections.observableArrayList();
        if (!this.getSubDepartments().isEmpty())
            subDepartments.forEach(sd -> {
                tmp.addAll(sd.getAllSubDepartments());
                tmp.add(sd);
            });
        else
            return getSubDepartments();
        return tmp;
    }

    public boolean isSubDepartment() {
        return isSubDepartment.get();
    }

    public void setSubDepartment(boolean subDepartment) {
        isSubDepartment.set(subDepartment);
    }

    @Override
    public String toString() {
        return getName();
    }

}
