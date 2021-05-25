package BE;

import GUI.Controller.DPT.DepartmentViewController;
import GUI.Model.DepartmentModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(-1);
    private StringProperty name = new SimpleStringProperty("");
    private ObjectProperty<User> manager = new SimpleObjectProperty();
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
        return manager.get();
    }

    public ObjectProperty<User> managerProperty() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager.set(manager);
        DepartmentModel.getInstance().addDepartment(this);
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set(id);

    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;

    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public ObservableList<Department> getSubDepartments() {
        return subDepartments;
    }

    public void addSubDepartment(Department subDepartment){
        subDepartments.add(subDepartment);
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
            subDepartments.forEach(sd -> {
                tmp.addAll(sd.getAllSubDepartments());
                tmp.add(sd);
            });
        else
            return getSubDepartments();
        return tmp;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
