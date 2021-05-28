package BE;


import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class User {
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(-1);
    private StringProperty firstName = new SimpleStringProperty("");
    private StringProperty lastName = new SimpleStringProperty("");
    private StringProperty userName = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private Enum userRole;
    private SimpleIntegerProperty password = new SimpleIntegerProperty(-1);
    private final ObjectProperty<Integer> phone = new SimpleObjectProperty<>(-1);
    private List<ScreenBit> assignedScreenBits = new ArrayList<>();
    private final StringProperty photoPath = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private Enum gender;

    public User() {
    }

    public User(String userName) {
        this.userName.set(userName);
    }

    public User(String fName, String lName, String email, int phone) {
        this.firstName.setValue(fName);
        this.lastName.setValue(lName);
        this.email.setValue(email);
        this.phone.set(phone);
    }

    public User(int id, String firstName, String lastName, String userName, String email, String phoneNumber, int userRole, int password, Enum gender, String photoPath, Enum department, String title, List<ScreenBit> assignedScreenBits) {
        this.id = new SimpleObjectProperty<>(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.email = new SimpleStringProperty(email);
        this.phone.set(Integer.valueOf(phoneNumber));
        this.setUserRole(userRole);
        this.password = new SimpleIntegerProperty(password);
        this.gender = gender;
        this.photoPath.set(photoPath);
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
        this.assignedScreenBits = new ArrayList<>();
    }


    public User(int id, String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.userName.set(userName);
        this.email.set(email);
        this.phone.set(phoneNumber);
        setUserRole(userRole);
        this.password.set(password);
        this.gender = gender;
        this.photoPath.set(photoPath);
        this.title.set(title);
    }

    public User(String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.userName.set(userName);
        this.email.set(email);
        this.phone.set(phoneNumber);
        this.setUserRole(userRole);
        this.password.set(password);
        this.gender = gender;
        this.photoPath.set(photoPath);
        this.title.set(title);
    }

    public User(int id, String firstName, String lastName, String userName, String email, int phoneNumber, int userRole, int password, int gender, int department, String title) {
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.userName.set(userName);
        this.email.set(email);
        this.phone.set(phoneNumber);
        setUserRole(userRole);
        this.password.set(password);
        this.gender = Gender.valueOf(gender);
        this.title.set(title);
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
            case 3:
                this.userRole = UserType.HR;
                break;
        }


    }


    public int getId() {
        return this.id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return this.id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return this.firstName.get();
    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return this.lastName.get();
    }

    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getUserName() {
        return this.userName.get();
    }

    public StringProperty userNameProperty() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getEmail() {
        return this.email.get();
    }

    public StringProperty emailProperty() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Enum getUserRole() {
        return this.userRole;
    }

    public void setUserRole(Enum userRole) {
        this.userRole = userRole;
    }

    public int getPassword() {
        return this.password.get();
    }

    public IntegerProperty passwordProperty() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password.set(password);
    }

    public int getPhone() {
        return this.phone.get();
    }

    public ObjectProperty<Integer> phoneProperty() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    public List<ScreenBit> getAssignedScreenBits() {
        return this.assignedScreenBits;
    }

    public void setAssignedScreenBits(List<ScreenBit> assignedScreenBits) {
        this.assignedScreenBits = assignedScreenBits;
    }

    public StringProperty getFullNameProperty() {
        StringProperty fullNameProperty = new SimpleStringProperty(this.firstName.get() + " " + this.lastName.get());
        this.firstName.addListener((observableValue, s, t1) -> fullNameProperty.setValue(t1 + " " + lastNameProperty().get()));
        this.lastName.addListener((observableValue, s, t1) -> fullNameProperty.setValue(this.firstName + " " + t1));
        return fullNameProperty;
    }

    public StringProperty getAssignedScreensString() {
        return new SimpleStringProperty(getAssignedScreenBits().stream().map(ScreenBit::getName).collect(Collectors.joining(", ")));
    }


    public void setPhone(final Integer phone) {
        this.phone.set(phone);
    }

    public String getPhotoPath() {
        return this.photoPath.get();
    }

    public StringProperty photoPathProperty() {
        return this.photoPath;
    }

    public void setPhotoPath(final String photoPath) {
        this.photoPath.set(photoPath);
    }

    public String getTitle() {
        return this.title.get();
    }

    public StringProperty titleProperty() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title.set(title);
    }


    public Enum getGender() {
        return this.gender;
    }

    public void setGender(final Enum gender) {
        this.gender = gender;
    }

    public String toCSV() {
        return this.id.getValue() + ";" + this.firstName.get() + ";" + this.lastName.get() + ";" + this.userName.get() + ";" + this.email.get() + ";" + this.phone.get() + ";" + this.userRole.ordinal() + ";" + this.password.get() + ";" + this.gender.ordinal() + ";" + this.title.get();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.getId(), user.getId()) &&
                Objects.equals(this.getFirstName(), user.getFirstName()) &&
                Objects.equals(this.getLastName(), user.getLastName()) &&
                Objects.equals(this.getUserName(), user.getUserName()) &&
                Objects.equals(this.getEmail(), user.getEmail()) &&
                Objects.equals(this.getUserRole(), user.getUserRole()) &&
                Objects.equals(this.getPassword(), user.getPassword()) &&
                Objects.equals(this.getPhone(), user.getPhone()) &&
                Objects.equals(this.getAssignedScreenBits(), user.getAssignedScreenBits()) &&
                Objects.equals(this.getPhotoPath(), user.getPhotoPath()) &&
                Objects.equals(this.getTitle(), user.getTitle()) &&
                Objects.equals(this.getGender(), user.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getFirstName(), this.getLastName(), this.getUserName(), this.getEmail(), this.getUserRole(), this.getPassword(), this.getPhone(), this.getAssignedScreenBits(), this.getPhotoPath(), this.getTitle(), this.getGender());
    }

    @Override
    public String toString() {
        return this.userName.getValue() + " - " + this.firstName.getValue() + " " + this.lastName.getValue();
    }

    /**
     * Adds a ScreenBit to the User's list of assigned ScreenBits.
     * @param screenBit assigned ScreenBit
     */
    public void addScreenAssignment(final ScreenBit screenBit) {
        this.assignedScreenBits.add(screenBit);
    }

    /**
     * Removes a ScreenBit from the User's list of assigned ScreenBits.
     * @param screenBit ScreenBit to be removed.
     */
    public void removeScreenBit(final ScreenBit screenBit) {
        this.assignedScreenBits.remove(screenBit);
    }

    /**
     * Updates a ScreenBit in the User's list of assigned ScreenBits.
     * @param oldScreenBit ScreenBit to be removed.
     * @param newScreenBit ScreenBit to be removed.
     */
    public void updateScreenBit(final ScreenBit oldScreenBit, ScreenBit newScreenBit) {
        this.assignedScreenBits.remove(oldScreenBit);
        this.assignedScreenBits.add(newScreenBit);
    }


}
