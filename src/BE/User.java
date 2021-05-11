package BE;


import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Enum userRole;
    private int password;
    private List<ScreenBit> assignedScreenBits;

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits.add(assignedScreenBits);
    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits.add(assignedScreenBits);
    }

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits = assignedScreenBits;
    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits = assignedScreenBits;
    }

    public User(int id, String firstName, String lastName, String userName, String email, int userRole, int password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits = new ArrayList<ScreenBit>();

    }

    public User(String firstName, String lastName, String userName, String email, int userRole, int password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        setUserRole(userRole);
        this.password = password;
        this.assignedScreenBits = new ArrayList<ScreenBit>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Enum getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        switch(userRole){
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

    public List<ScreenBit> getAssignedScreen() {
        return assignedScreenBits;
    }

    public void addScreenAssignment(ScreenBit assignedScreenBit) {
        assignedScreenBits.add(assignedScreenBit);
    }

    public void removeScreenAssignment(ScreenBit assignedScreenBit){assignedScreenBits.remove(assignedScreenBit);}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userName + " - " + firstName + " " +lastName;
    }
}
