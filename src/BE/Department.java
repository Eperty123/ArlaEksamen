package BE;

import GUI.Controller.DPT.DepartmentViewController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Department {
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(-1);
    private StringProperty name = new SimpleStringProperty("");
    private final ObjectProperty<User> manager = new SimpleObjectProperty();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Department> subDepartments = FXCollections.observableArrayList();
    private final BooleanProperty isSubDepartment = new SimpleBooleanProperty(false);
    private DepartmentViewController departmentViewController;

    public DepartmentViewController getDepartmentViewController() {
        return this.departmentViewController;
    }

    public void setDepartmentViewController(DepartmentViewController departmentViewController) {
        this.departmentViewController = departmentViewController;
    }

    public Department(ObjectProperty<Integer> id, StringProperty name, ObservableList<User> users, ObservableList<Department> subDepartments) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.subDepartments = subDepartments;
    }

    public Department(int id, String name, User manager) {
        this.id.set(id);
        this.name.set(name);
        this.manager.set(manager);
    }

    public Department(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    public Department(String name) {
        this.name.set(name);
    }

    public User getManager() {
        return this.manager.get();
    }

    public ObjectProperty<User> managerProperty() {
        return this.manager;
    }

    public void setManager(User manager) {
        this.manager.set(manager);
    }

    public ObjectProperty<Integer> idProperty() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id.set(id);

    }

    public int getId() {
        return this.id.get();
    }

    public String getName() {
        return this.name.get();
    }

    public StringProperty nameProperty() {
        return this.name;

    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public ObservableList<Department> getSubDepartments() {
        return this.subDepartments;
    }

    public void addSubDepartment(Department subDepartment){
        this.subDepartments.add(subDepartment);
    }

    public void setSubDepartments(ObservableList<Department> subDepartments) {
        this.subDepartments = subDepartments;
    }

    public void setSubDepartment(boolean b) {
        this.isSubDepartment.set(b);
    }

    public boolean getIsSubDepartment() {
        return this.isSubDepartment.get();
    }

    public ObservableList<Department> getAllSubDepartments() {
        ObservableList<Department> tmp = FXCollections.observableArrayList();
        if (!this.getSubDepartments().isEmpty())
            this.subDepartments.forEach(sd -> {
                tmp.addAll(sd.getAllSubDepartments());
                tmp.add(sd);
            });
        else
            return getSubDepartments();
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getManager(), that.getManager()) && Objects.equals(getUsers(), that.getUsers()) && Objects.equals(getSubDepartments(), that.getSubDepartments()) && Objects.equals(getIsSubDepartment(), that.getIsSubDepartment()) && Objects.equals(getDepartmentViewController(), that.getDepartmentViewController());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getManager(), getUsers(), getSubDepartments(), getIsSubDepartment(), getDepartmentViewController());
    }

    @Override
    public String toString() {
        return this.name.get();
    }

}
