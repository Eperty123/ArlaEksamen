package GUI.Model;

import BE.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    private static DataModel instance;

    private UserModel userModel;
    private ScreenModel screenModel;
    private DepartmentModel departmentModel;
    private MessageModel messageModel;
    private TitleModel titleModel;
    private BugModel bugModel;

    private ObservableList<User> users;
    private ObservableList<ScreenBit> screenBits;
    private ObservableList<Department> departments;
    private ObservableList<Message> messages;
    private ObservableList<String> titles;
    private ObservableList<Bug> bugs;
    private ObservableList<Bug> unresolvedBugs;



    private DataModel(){
        initialize();
    }

    private void initialize() {



        departmentModel = DepartmentModel.getInstance();
        messageModel = MessageModel.getInstance();
        screenModel = ScreenModel.getInstance();
        titleModel = TitleModel.getInstance();
        userModel = UserModel.getInstance();
        bugModel = BugModel.getInstance();

        unresolvedBugs = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();
        screenBits = FXCollections.observableArrayList();
        messages = FXCollections.observableArrayList();
        titles = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
        bugs = FXCollections.observableArrayList();

        departments.addAll(departmentModel.getAllDepartments());
        screenBits.addAll(screenModel.getAllScreenBits());
        messages.addAll(messageModel.getAllMessages());
        titles.addAll(titleModel.getTitles());
        users.addAll(userModel.getAllUsers());
        bugs.addAll(bugModel.getAllBugs());
    }

    public static DataModel getInstance(){
        return instance == null ? instance = new DataModel() : instance;
    }

    // _____ Title _____

    public void addTitle(String title){
        if(!titles.contains(title)){
            titleModel.addTitle(title);
            titles.add(title);
        }
    }

    public void deleteTitle(String title){
        if(titles.contains(title)){
            titleModel.deleteTitle(title);
            titles.remove(title);
        }
    }

    public void updateTitle(String oldTitle, String newTitle){
        titleModel.updateTitle(oldTitle, newTitle);
        titles.remove(oldTitle);
        titles.add(newTitle);
        updateUserTitles(oldTitle, newTitle);
    }

    private void updateUserTitles(String oldTitle, String newTitle) {
        for(User u: users){
            if(u.getTitle().equals(oldTitle)){
                u.setTitle(newTitle);
            }
        }
    }

    public ObservableList<String> getTitles() {
        return titles;
    }

    public void setTitles(ObservableList<String> titles) {
        this.titles = titles;
    }

    // _____ User _____

    public void addUser(User newUser, Department department) {
        if (users.stream().noneMatch(o -> o.getUserName().equals(newUser.getUserName()))) {
            users.add(newUser);
            addUserToDepartment(newUser, department);
        }
    }

    private void addUserToDepartment(User newUser, Department department) {
        departments.forEach(dpt -> {
            if(dpt.getId() == department.getId()){
                dpt.addUser(newUser);
            }
        });
    }

    // update to only use new user
    public void updateUser(User oldUser, User newUser, Department oldDepartment, Department newDepartment) {

        if(oldDepartment.getId() != newDepartment.getId() && oldUser.equals(newUser)){
            moveUser(oldUser, oldUser, oldDepartment, newDepartment);
        } else if (oldDepartment.getId() != newDepartment.getId() && !oldUser.equals(newUser)){
            moveUser(oldUser, newUser, oldDepartment, newDepartment);
        }
        userModel.updateUser(oldUser, newUser, oldDepartment, newDepartment);
        users.remove(oldUser);
        users.add(newUser);

    }

    private void moveUser(User oldUser, User newUser, Department oldDepartment, Department newDepartment) {
        departments.forEach(dpt -> {
            if(dpt.getId() == oldDepartment.getId()){ dpt.getUsers().remove(oldUser); }
        });
        departments.forEach(dpt -> {
            if(dpt.getId() == newDepartment.getId()){ dpt.getUsers().add(newUser); }
        });
    }

    public void updateUserDepartment(List<Department> departments){
        userModel.updateUserDepartment(departments);
        updateUserDepartmentRelation(departments);

    }

    private void updateUserDepartmentRelation(List<Department> departments) {
        //Delete users from Departments
        this.departments.forEach(dpt -> {
            dpt.getUsers().clear();
        });
        //Re-assign users to departments
        this.departments.forEach(dpt -> {
            departments.forEach(newDpt -> {
                if(dpt.getId() == newDpt.getId()){
                    dpt.setUsers(newDpt.getUsers());
                }
            });
        });
    }

    public void deleteUser(User user) {
        userModel.deleteUser(user);
        users.remove(user);
        deleteUserFromDepartments(user);
        unAssignUserFromScreens(user);

    }

    private void unAssignUserFromScreens(User user) {
        screenBits.forEach(screenBit -> {
            screenBit.getAssignedUsers().remove(user);
        });
    }

    private void deleteUserFromDepartments(User user) {
        departments.forEach(dpt -> {
            dpt.getUsers().remove(user);
        });
    }

    public User getUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId)
                return user;
        }
        return null;
    }

    public User getUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    public User getUser(String firstName, String lastName) {
        for (User user : users) {
            if (user.getUserName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

    public List<User> getAllUsersByRole(UserType role) {
        var filteredUsers = new ArrayList<User>();
        for (User user : users) {
            if (user.getUserRole().equals(role))
                filteredUsers.add(user);
        }
        return filteredUsers;
    }


    public ObservableList<User> getUsers() {
        return users;
    }



    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    // _____ ScreenBits _____


    public void addScreenBit(ScreenBit newScreenBit) {

        if (screenBits.stream().noneMatch(o -> o.getName().equals(newScreenBit.getName()))) {
            newScreenBit.setId(ScreenModel.getInstance().addScreenBit(newScreenBit));
            screenBits.add(newScreenBit);
        }
    }

    /**
     * Deletes the specified ScreenBit from the database.
     *
     * @param screenBit object containing information to identify the row in the database.
     */
    public void deleteScreenBit(ScreenBit screenBit) {
        screenModel.deleteScreenBit(screenBit);
        users.forEach(u -> {
            if(u.getAssignedScreenBits().contains(screenBit)){ u.removeScreenBit(screenBit); }
        });
    }


    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) {
        screenModel.updateScreenBit(newScreenBit, oldScreenBit);
        updateScreenBitsOnUsers(newScreenBit, oldScreenBit);
    }

    private void updateScreenBitsOnUsers(ScreenBit newScreenBit, ScreenBit oldScreenBit) {
        users.forEach(u -> { if(u.getAssignedScreenBits().contains(oldScreenBit)){ u.updateScreenBit(oldScreenBit, newScreenBit); } });
    }

    public void assignScreenBitRights(User user, ScreenBit screenBit) {
        screenModel.assignScreenBitRights(user, screenBit);
        assignScreenBitUserRights(user, screenBit);
    }

    public void assignScreenBitRights(List<User> users, ScreenBit screenBit) {
        screenModel.assignScreenBitRights(users, screenBit);
        users.forEach(u -> {assignScreenBitUserRights(u, screenBit);});
    }

    private void assignScreenBitUserRights(User user, ScreenBit screenBit) {
        users.forEach(u -> { if(u.getId() == user.getId()) {u.addScreenAssignment(screenBit);} });
        screenBits.forEach(s -> { if(s.getId() == screenBit.getId()) {s.addAssignedUser(user);} });
    }

    public void removeScreenBitRights(User user, ScreenBit screenBit) {
        screenModel.removeScreenBitRights(user, screenBit);
        deleteScreenBitUserRights(user, screenBit);
    }

    public void removeScreenBitRights(List<User> users, ScreenBit screenBit) {
        screenModel.removeScreenBitRights(users, screenBit);
        users.forEach(u -> {deleteScreenBitUserRights(u, screenBit);});
    }

    private void deleteScreenBitUserRights(User user, ScreenBit screenBit) {
        users.forEach(u -> { if(u.getId() == user.getId()) {u.removeScreenBit(screenBit);} });
        screenBits.forEach(s -> { if(s.getId() == screenBit.getId()) {s.removeUser(user);} });
    }

    public ObservableList<ScreenBit> getScreenBits() {
        return screenBits;
    }

    public void setScreenBits(ObservableList<ScreenBit> screenBits) {
        this.screenBits = screenBits;
    }

    // _____ Departments _____

    public void addDepartment(Department newDepartment) {
        departmentModel.addDepartment(newDepartment);
        departments.add(newDepartment);
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        departmentModel.addSubDepartment(department, subDepartment);
        addSubDepartmentToDepartment(department, subDepartment);
    }

    private void addSubDepartmentToDepartment(Department department, Department subDepartment) {
        departments.forEach(dpt -> {
            if(dpt.getId() == department.getId()){dpt.addSubDepartment(subDepartment);}
        });
    }

    public void deleteDepartment(Department d) {
        departmentModel.deleteDepartment(d);
        departments.remove(d);
    }

    public void updateDepartment(Department department) {
        departmentModel.updateDepartment(department);
        departments.forEach(dpt -> {
            if( dpt.getId() == department.getId()){
                dpt.setName(department.getName());
                dpt.setManager(department.getManager());
            }
        });
    }

    public ObservableList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ObservableList<Department> departments) {
        this.departments = departments;
    }

    public Department getDepartment(String departmentName) {
        for(Department d : departments){
            if(d.getName().equals(departmentName)){
                return d;
            }
        }
        return null;
    }



    public List<Department> getSuperDepartment() {
        return departmentModel.getSuperDepartment();
    }

    // _____ Messages _____

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {
        messageModel.addMessage(user, newMessage, assignedScreenBits);
        messages.add(newMessage);
        addMessageToScreenBits(newMessage, assignedScreenBits);
        bookScreenBitTimeTables(newMessage, assignedScreenBits);
    }

    private void addMessageToScreenBits( Message newMessage, List<ScreenBit> assignedScreenBits) {
        assignedScreenBits.forEach( assignedScreenBit -> {
            screenBits.forEach( screenBit -> {
                if (assignedScreenBit.getId() == screenBit.getId()) {
                    screenBit.addMessage(newMessage);
                }
            });
        });
    }

    private void bookScreenBitTimeTables(Message newMessage, List<ScreenBit> assignedScreenBits) {
        assignedScreenBits.forEach( screenBit -> { screenBit.bookTimeSlots(newMessage); });
    }

    public void deleteMessage(Message message){
        messageModel.deleteMessage(message);
        messages.remove(message);
        deleteMessageFromScreenBits(message);
    }

    private void deleteMessageFromScreenBits( Message message) {
            screenBits.forEach( screenBit -> {
                if (screenBit.getMessages().contains(message)) {
                    screenBit.removeMessage(message);
                }
            });
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        messageModel.updateMessage(oldMessage, newMessage);
        messages.remove(oldMessage);
        messages.add(newMessage);
        updateMessageOnScreenBits(oldMessage, newMessage);

    }

    private void updateMessageOnScreenBits(Message oldMessage, Message newMessage) {
        screenBits.forEach(s -> {
            s.removeMessage(oldMessage);
            s.addMessage(newMessage);
        });
    }

    public List<Message> getUsersMessages(User user) {
        return messageModel.getUsersMessages(user);
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ObservableList<Message> messages) {
        this.messages = messages;
    }

    public void loadScreenBitsMessages(ScreenBit screen) {
        messageModel.loadScreenBitsMessages(screen);
    }

    // _____ Bugs _____

    public void addBug(Bug newBug) {
        bugModel.addBug(newBug);
    }

    public void updateBug(Bug oldBug, Bug newBug) {
        bugModel.updateBug(oldBug, newBug);
        bugs.remove(oldBug);
        bugs.add(newBug);
    }

    public void deleteBug(Bug bug) {
        bugModel.deleteBug(bug);
        bugs.remove(bug);
    }

    public ObservableList<Bug> getAllBugs() {
        return bugs;
    }

    public ObservableList<Bug> getAllUnresolvedBugs() {
        ObservableList<Bug> unresolvedBugs = FXCollections.observableArrayList();
        for(Bug b : bugs){
            if(!b.isBugResolved()){
                unresolvedBugs.add(b);
            }
        }
        return unresolvedBugs;
    }

    public void updateAllBugs() {
        var managerBugs = bugs;
        List<Bug> _unresolvedBugs = new ArrayList<>();


        // Loop through the DAL bugs. This is to not let the Snackbar spam the admin about incoming bug reports for each bug.
        managerBugs.forEach((x) -> {
            if (!x.isBugResolved()) {
                _unresolvedBugs.add(x);
            }
        });

        // Now set the unresolved bug ObservableList.
        unresolvedBugs.setAll(_unresolvedBugs);

    }

}
