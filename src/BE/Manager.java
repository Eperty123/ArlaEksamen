package BE;

import java.util.List;

public class Manager extends User{
    private List<Message> messages;

    public Manager(int id, String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        super(id, firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public Manager(String firstName, String lastName, String userName, String email, int userRole, int password, ScreenBit assignedScreenBits) {
        super(firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public Manager(int id, String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        super(id, firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public Manager(String firstName, String lastName, String userName, String email, int userRole, int password, List<ScreenBit> assignedScreenBits) {
        super(firstName, lastName, userName, email, userRole, password, assignedScreenBits);
    }

    public Manager(int id, String firstName, String lastName, String userName, String email, int userRole, int password) {
        super(id, firstName, lastName, userName, email, userRole, password);
    }

    public Manager(String firstName, String lastName, String userName, String email, int userRole, int password) {
        super(firstName, lastName, userName, email, userRole, password);
    }

    public List<Message> getMessages() {
        return this.messages;

    }

}
