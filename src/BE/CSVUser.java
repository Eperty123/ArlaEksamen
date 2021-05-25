package BE;

import java.util.List;

public class CSVUser extends User {

    private int departmentId;

    public CSVUser() {
    }

    public CSVUser(String userName) {
        super(userName);
    }

    public CSVUser(User user) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setUserName(user.getUserName());
        setEmail(user.getEmail());
        setPhone(user.getPhone());
        setUserRole(user.getUserRole());
        setPassword(user.getPassword());
        setGender(user.getGender());
        setTitle(user.getTitle());
    }

    public CSVUser(User user, int departmentId) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setUserName(user.getUserName());
        setEmail(user.getEmail());
        setPhone(user.getPhone());
        setUserRole(user.getUserRole());
        setPassword(user.getPassword());
        setGender(user.getGender());
        setTitle(user.getTitle());
        setDepartmentId(departmentId);
    }

    public CSVUser(String fName, String lName, String email, int phone) {
        super(fName, lName, email, phone);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, String phoneNumber, int userRole, int password, Enum gender, String photoPath, Enum department, String title, List<ScreenBit> assignedScreenBits) {
        super(id, firstName, lastName, userName, email, phoneNumber, userRole, password, gender, photoPath, department, title, assignedScreenBits);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        super(id, firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public CSVUser(String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        super(firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        super(id, firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public CSVUser(String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        super(firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, int userRole, int password) {
        super(id, firstName, lastName, userName, email, userRole, password);
    }

    public CSVUser(String firstName, String lastName, String userName, String email, int userRole, int password) {
        super(firstName, lastName, userName, email, userRole, password);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {
        super(id, firstName, lastName, userName, email, password, userRole, phoneNumber, gender, photoPath, title);
    }

    public CSVUser(String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {
        super(firstName, lastName, userName, email, password, userRole, phoneNumber, gender, photoPath, title);
    }

    public CSVUser(int id, String firstName, String lastName, String userName, String email, int phoneNumber, int userRole, int password, int gender, int department, String title) {
        super(id, firstName, lastName, userName, email, phoneNumber, userRole, password, gender, department, title);
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toCSV() {
        return getId() + ";" + getFirstName() + ";" + getLastName() + ";" + getUserName() + ";" + getEmail() + ";" + getPhone() + ";" + getUserRole().ordinal() + ";" + getPassword() + ";" + getGender().ordinal() + ";" + getDepartmentId() + ";" + getTitle();
    }
}
