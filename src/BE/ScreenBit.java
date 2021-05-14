package BE;

import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScreenBit {
    private int id;
    private String name;
    private String screenInfo;
    private List<User> assignedUsers;
    private Pane pane;
    private List<Message> messages;
    private HashMap<LocalDateTime, Boolean> timeTable;

    public ScreenBit(String name) {
        this.name = name;
        this.screenInfo = "void";
        this.assignedUsers = new ArrayList<>();
        this.timeTable = new HashMap<>();
    }

    public ScreenBit(String name, String screenInfo) {
        this.name = name;
        this.screenInfo = screenInfo;
        this.assignedUsers = new ArrayList<>();this.timeTable = new HashMap<>();

    }

    public ScreenBit(int id, String screenName, String screenInfo) {
        this.id = id;
        this.name = screenName;
        this.screenInfo = screenInfo;
        this.assignedUsers = new ArrayList<>();
        this.timeTable = new HashMap<>();
    }

    public ScreenBit(int id, String name, String screenInfo, List<User> assignedUsers, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.screenInfo = screenInfo;
        this.assignedUsers = assignedUsers;
        this.messages = messages;
        this.timeTable = new HashMap<>();
    }

    /**
     * Get the Screen id.
     *
     * @return Returns the assigned id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Screen's id.
     *
     * @param id The id to use.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the Screen's Screen info.
     *
     * @return Returns the Screen info.
     */
    public String getScreenInfo() {
        return screenInfo;
    }

    /**
     * Set the Screen's Screen info.
     *
     * @param screenInfo The screen info to use. Please make sure this is valid.
     */
    public void setScreenInfo(String screenInfo) {
        this.screenInfo = screenInfo;
    }

    /**
     * Get the Screen's name.
     *
     * @return Returns the Screen name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Screen's name.
     *
     * @param name The name to use.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get all Users assigned to this Screen.
     *
     * @return Returns a List of Users assigned to this Screen.
     */
    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    /**
     * Assign a List of Users to this Screen.
     *
     * @param assignedUsers The List of Users to assign.
     */
    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    /**
     * Assign a User to this Screen.
     *
     * @param user The User to assign.
     */
    public void addUser(User user) {
        this.assignedUsers.add(user);
    }

    /**
     * Unassign a User from this Screen.
     *
     * @param user The User to unassign.
     */
    public void removeUser(User user) {
        assignedUsers.remove(user);
    }

    /**
     * Get the Screen's Pane.
     *
     * @return Returns the Pane.
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Set the Screen's Pane.
     *
     * @param pane The Pane to assign.
     */
    public void setPane(Pane pane) {
        this.pane = pane;
    }



    // TODO Jonas javadoc
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public void removeMessage(Message message){
        this.messages.remove(message);
    }

    public HashMap<LocalDateTime, Boolean> getTimeTable() {
        return timeTable;
    }
  
    @Override
    public String toString() {
        return getName();
    }
}
