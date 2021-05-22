package BE;


import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(-1);
    private StringProperty firstName = new SimpleStringProperty("");
    private StringProperty lastName = new SimpleStringProperty("");
    private StringProperty userName = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private Enum userRole;
    private SimpleIntegerProperty password = new SimpleIntegerProperty(-1);
    private ObjectProperty<Integer> phone = new SimpleObjectProperty<>(-1);
    private List<ScreenBit> assignedScreenBits = new ArrayList<>();
    private StringProperty photoPath = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private Enum department;
    private Enum gender;

    public User(String fName, String lName, String email, int phone) {
        this.firstName.setValue(fName);
        this.lastName.setValue(lName);
        this.email.setValue(email);
        this.phone.set(phone);
    }

    public User(int id, String firstName, String lastName, String userName, String email, String phoneNumber, int userRole, int password, Enum gender, String photoPath, Enum department, String title, List<ScreenBit> assignedScreenBits) {

        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.phone.set(Integer.valueOf(phoneNumber));
        setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.gender = gender;
        this.photoPath.set(photoPath);
        this.department = department;
        this.title.set(title);
        this.assignedScreenBits = assignedScreenBits;
    }

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        this.id = new SimpleObjectProperty<>(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.assignedScreenBits.add(assignedScreenBits);
    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.assignedScreenBits.add(assignedScreenBits);
    }

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        this.id = new SimpleObjectProperty<>(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.assignedScreenBits = assignedScreenBits;
    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.assignedScreenBits = assignedScreenBits;
    }

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password) {
        this.id = new SimpleObjectProperty<>(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.assignedScreenBits = new ArrayList<ScreenBit>();
    }

    public void setUserRole(int userRole) {
        switch (userRole) {
            case 0:
                this.userRole = UserType.Admin;
                break;
            case 1:
                this.userRole = UserType.User;
                break;
            case 2:
                this.userRole = UserType.Manager;
                break;
        }


    }

    public int getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Enum getUserRole() {
        return userRole;
    }

    public void setUserRole(Enum userRole) {
        this.userRole = userRole;
    }

    public int getPassword() {
        return password.get();
    }

    public IntegerProperty passwordProperty() {
        return password;
    }

    public void setPassword(int password) {
        this.password.set(password);
    }

    public int getPhone() {
        return phone.get();
    }

    public ObjectProperty<Integer> phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    public List<ScreenBit> getAssignedScreenBits() {
        return assignedScreenBits;
    }

    public void setAssignedScreenBits(List<ScreenBit> assignedScreenBits) {
        this.assignedScreenBits = assignedScreenBits;
    }

    public StringProperty getFullNameProperty() {
        StringProperty fullNameProperty = new SimpleStringProperty(firstName.get() + " " + lastName.get());
        firstName.addListener((observableValue, s, t1) -> fullNameProperty.setValue(t1 + " " + lastNameProperty().get()));
        lastName.addListener((observableValue, s, t1) -> fullNameProperty.setValue(firstName + " " + t1));
        return fullNameProperty;
    }

    public StringProperty getAssignedScreensString() {
        return new SimpleStringProperty(getAssignedScreenBits().stream().map(ScreenBit::getName).collect(Collectors.joining(", ")));
    }


    public void setPhone(Integer phone) {
        this.phone.set(phone);
    }

    public String getPhotoPath() {
        return photoPath.get();
    }

    public StringProperty photoPathProperty() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath.set(photoPath);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Enum getDepartment() {
        return department;
    }

    public void setDepartment(Enum department) {
        this.department = department;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
}
