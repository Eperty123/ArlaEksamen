package GUI.Model;

import BE.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataModel {

    private static DataModel instance;

    private UserModel userModel;
    private ScreenModel screenModel;
    private DepartmentModel departmentModel;
    private SettingsModel settingsModel;
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
    private ObservableList<Settings> settings;


    private DataModel() {
        initialize();
    }

    private void initialize() {
        departmentModel = DepartmentModel.getInstance();
        settingsModel = SettingsModel.getInstance();
        messageModel = MessageModel.getInstance();
        screenModel = ScreenModel.getInstance();
        titleModel = TitleModel.getInstance();
        userModel = UserModel.getInstance();
        bugModel = BugModel.getInstance();

        unresolvedBugs = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();
        screenBits = FXCollections.observableArrayList();
        messages = FXCollections.observableArrayList();
        settings = FXCollections.observableArrayList();
        titles = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
        bugs = FXCollections.observableArrayList();

        departments.addAll(departmentModel.getAllDepartments());
        screenBits.addAll(screenModel.getAllScreenBits());
        messages.addAll(messageModel.getAllMessages());
        settings.addAll(settingsModel.getAllSettings());
        titles.addAll(titleModel.getTitles());
        users.addAll(userModel.getAllUsers());
        bugs.addAll(bugModel.getAllBugs());
    }

    /**
     * Returns the instance of the DataModel Singleton.
     * @return
     */
    public static DataModel getInstance() {
        return instance == null ? instance = new DataModel() : instance;
    }

    // _____ Title _____

    /**
     * Adds a title to the database, and the list of titles in DataModel.
     * @param title
     * @throws SQLException
     */
    public void addTitle(String title) throws SQLException {
        if (!titles.contains(title)) {
            titleModel.addTitle(title);
            titles.add(title);
        }
    }

    /**
     * Deletes a title from the database, and the list of titles in DataModel.
     * @param title
     * @throws SQLException
     */
    public void deleteTitle(String title) throws SQLException {
        if (titles.contains(title)) {
            titleModel.deleteTitle(title);
            titles.remove(title);
        }
    }

    /**
     * Updates a title in the database, and in the list of titles in DataModel.
     * @param oldTitle
     * @param newTitle
     * @throws SQLException
     */
    public void updateTitle(String oldTitle, String newTitle) throws SQLException {
        titleModel.updateTitle(oldTitle, newTitle);
        titles.remove(oldTitle);
        titles.add(newTitle);
        updateUserTitles(oldTitle, newTitle);
    }

    /**
     * Updates the title of all users who were assigned the old title.
     * @param oldTitle
     * @param newTitle
     */
    private void updateUserTitles(String oldTitle, String newTitle) {
        for (User u : users) {
            if (u.getTitle().equals(oldTitle)) {
                u.setTitle(newTitle);
            }
        }
    }

    /**
     * Returns a list of all titles.
     * @return
     */
    public ObservableList<String> getTitles() {
        return titles;
    }


    // _____ User _____

    /**
     * Adds a user to the database, and to the list of Users in DataModel.
     * @param newUser
     * @param department
     * @throws SQLException
     */
    public void addUser(User newUser, Department department) throws SQLException {
        if (users.stream().noneMatch(
                o -> o.getUserName().equals(newUser.getUserName()))) {
            userModel.addUser(newUser, department);
            users.add(newUser);
            addUserToDepartment(newUser, department);
        }
    }

    /**
     * Adds the user to the specified Department in the list of Departments in DataModel.
     * @param newUser
     * @param department
     */
    private void addUserToDepartment(User newUser, Department department) {
        departments.forEach(dpt -> {
            if (dpt.getId() == department.getId()) {
                dpt.addUser(newUser);
            }
        });
    }

    /**
     * Updates the user in the database, and in the list of Users in DataModel.
     * @param user
     * @param department
     * @throws SQLException
     */
    public void updateUser(User user, Department department) throws SQLException {

        userModel.updateUser(user, department);
        User userToDelete = new User();
        for (User u : users) {
            if (u.getId() == user.getId()) {
                userToDelete = u;
            }
        }
        users.remove(userToDelete);
        users.add(user);
        moveUser(user, department);

    }

    /**
     * Moves a User to the specified department.
     * @param user
     * @param department
     */
    private void moveUser(User user, Department department) {
        for (Iterator<Department> dptIterator = departments.iterator(); dptIterator.hasNext(); ) {
            Department dpt = dptIterator.next();

            for (Iterator<User> userIterator = dpt.getUsers().iterator(); userIterator.hasNext(); ) {
                User u = userIterator.next();
                if (u.getId() == user.getId()) {
                    userIterator.remove();
                }
            }
            if (dpt.getId() == department.getId()) {
                dpt.addUser(user);
            }
        }
    }

    /**
     * Updates the DepartmentUser table in the database, and the User-Department relations in DataModel.
     * @param departments
     * @throws SQLException
     */
    public void updateUserDepartment(List<Department> departments) throws SQLException {
        userModel.updateUserDepartment(departments);
        updateUserDepartmentRelation(departments);
    }

    /**
     * Updates the DepartmentUser table in the database, and the User-Department relations in DataModel.
     * @param departments
     */
    private void updateUserDepartmentRelation(List<Department> departments) {
        //Delete users from Departments
        this.departments.forEach(dpt -> {
            dpt.getUsers().clear();
        });
        //Re-assign users to departments
        this.departments.forEach(dpt -> {
            departments.forEach(newDpt -> {
                if (dpt.getId() == newDpt.getId()) {
                    dpt.setUsers(newDpt.getUsers());
                }
            });
        });
    }

    /**
     * Deletes a User from the database, and from the list of Users in DataModel.
     * @param user
     * @throws SQLException
     */
    public void deleteUser(User user) throws SQLException {
        userModel.deleteUser(user);
        users.remove(user);
        deleteUserFromDepartments(user);
        unAssignUserFromScreens(user);

    }

    /**
     * Removes the user from all ScreenBits where the object is assigned.
     * @param user
     */
    private void unAssignUserFromScreens(User user) {
        screenBits.forEach(screenBit -> {
            screenBit.getAssignedUsers().remove(user);
        });
    }

    /**
     * Removes the User from all departments in DataModel.
     * @param user
     */
    private void deleteUserFromDepartments(User user) {
        departments.forEach(dpt -> {
            dpt.getUsers().remove(user);
        });
    }

    /**
     * Get User by id.
     * @param userId
     * @return
     */
    public User getUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId)
                return user;
        }
        return null;
    }

    /**
     * Get User by UserName.
     * @param userName
     * @return
     */
    public User getUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    /**
     * Get User by firstName and lastName.
     * @param firstName
     * @param lastName
     * @return
     */
    public User getUser(String firstName, String lastName) {
        for (User user : users) {
            if (user.getUserName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

    /**
     * Get Users by UserType.
     * @param role
     * @return
     */
    public List<User> getAllUsersByRole(UserType role) {
        var filteredUsers = new ArrayList<User>();
        for (User user : users) {
            if (user.getUserRole().equals(role))
                filteredUsers.add(user);
        }
        return filteredUsers;
    }

    /**
     * Get all users.
     * @return
     */
    public ObservableList<User> getUsers() {
        return users;
    }

    /**
     * Set Users.
     * @param users
     */
    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    // _____ ScreenBits _____

    /**
     * Add ScreenBit to the database, and DataModel's list of ScreenBits - if it does not already exist.
     * @param newScreenBit
     * @throws SQLException
     */
    public void addScreenBit(ScreenBit newScreenBit) throws SQLException {

        if (screenBits.stream().noneMatch(o -> o.getName().equals(newScreenBit.getName()))) {
            ScreenModel.getInstance().addScreenBit(newScreenBit);
            screenBits.add(newScreenBit);
        }
    }

    /**
     * Deletes the specified ScreenBit from the database.
     *
     * @param screenBit object containing information to identify the row in the database.
     */
    public void deleteScreenBit(ScreenBit screenBit) throws SQLException {
        screenModel.deleteScreenBit(screenBit);
        users.forEach(u -> {
            if (u.getAssignedScreenBits().contains(screenBit)) {
                u.removeScreenBit(screenBit);
            }
        });
    }

    /**
     * Updates a ScreenBit in the database, and in the list of ScreenBits in DataModel.
     * @param newScreenBit
     * @param oldScreenBit
     * @throws SQLException
     */
    public void updateScreenBit(ScreenBit newScreenBit, ScreenBit oldScreenBit) throws SQLException {
        screenModel.updateScreenBit(newScreenBit, oldScreenBit);
        updateScreenBitsOnUsers(newScreenBit, oldScreenBit);
    }

    /**
     * Updates the ScreenBit on all users in DataModel, where it is assigned.
     * @param newScreenBit
     * @param oldScreenBit
     */
    private void updateScreenBitsOnUsers(ScreenBit newScreenBit, ScreenBit oldScreenBit) {
        users.forEach(u -> {
            if (u.getAssignedScreenBits().contains(oldScreenBit)) {
                u.updateScreenBit(oldScreenBit, newScreenBit);
            }
        });
    }

    /**
     * Assigns ScreenBit rights to the User, both in DataModel, and in the database.
     * @param user
     * @param screenBit
     * @throws SQLException
     */
    public void assignScreenBitRights(User user, ScreenBit screenBit) throws SQLException {
        screenModel.assignScreenBitRights(user, screenBit);
        assignScreenBitUserRights(user, screenBit);
    }

    /**
     * Assigns ScreenBit rights to the list of Users, both in DataModel, and in the database.
     * @param users
     * @param screenBit
     * @throws SQLException
     */
    public void assignScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException {
        screenModel.assignScreenBitRights(users, screenBit);
        users.forEach(u -> {
            assignScreenBitUserRights(u, screenBit);
        });
    }

    /**
     * Assigns ScreenBit rights to the User, both in DataModel, and in the database.
     * @param user
     * @param screenBit
     */
    private void assignScreenBitUserRights(User user, ScreenBit screenBit) {
        users.forEach(u -> {
            if (u.getId() == user.getId()) {
                u.addScreenAssignment(screenBit);
            }
        });
        screenBits.forEach(s -> {
            if (s.getId() == screenBit.getId()) {
                s.addAssignedUser(user);
            }
        });
    }

    /**
     * Removes the association between User and ScreenBit.
     * @param user
     * @param screenBit
     * @throws SQLException
     */
    public void removeScreenBitRights(User user, ScreenBit screenBit) throws SQLException {
        screenModel.removeScreenBitRights(user, screenBit);
        deleteScreenBitUserRights(user, screenBit);
    }

    /**
     * Removes the association between the list of Users and the ScreenBit.
     * @param users
     * @param screenBit
     * @throws SQLException
     */
    public void removeScreenBitRights(List<User> users, ScreenBit screenBit) throws SQLException {
        screenModel.removeScreenBitRights(users, screenBit);
        users.forEach(u -> {
            deleteScreenBitUserRights(u, screenBit);
        });
    }

    /**
     * Deletes the association between User and ScreenBit.
     * @param user
     * @param screenBit
     */
    private void deleteScreenBitUserRights(User user, ScreenBit screenBit) {
        users.forEach(u -> {
            if (u.getId() == user.getId()) {
                u.removeScreenBit(screenBit);
            }
        });
        screenBits.forEach(s -> {
            if (s.getId() == screenBit.getId()) {
                s.removeUser(user);
            }
        });
    }

    /**
     * Returns a list of all ScreenBits.
     * @return
     */
    public ObservableList<ScreenBit> getScreenBits() {
        return screenBits;
    }



    // _____ Departments _____

    /**
     * Adds a department to the database, and DataModel's list of Departments.
     * @param newDepartment
     * @throws SQLException
     */
    public void addDepartment(Department newDepartment) throws SQLException {
        departmentModel.addDepartment(newDepartment);
        departments.add(newDepartment);
    }

    /**
     * Adds a sub department to a department in the database, and DataModel's list of Departments.
     * @param department
     * @param subDepartment
     * @throws SQLException
     */
    public void addSubDepartment(Department department, Department subDepartment) throws SQLException {
        departmentModel.addSubDepartment(department, subDepartment);
    }

    /**
     * Deletes a department from the database, and DataModel's list of Departments.
     * @param d
     * @throws SQLException
     */
    public void deleteDepartment(Department d) throws SQLException {
        departmentModel.deleteDepartment(d);
        departments.forEach(dpt -> {
            if (dpt.getSubDepartments().contains(d))
                dpt.getSubDepartments().remove(d);
        });
        departments.remove(d);
    }

    /**
     * Updates a department in the database, and DataModel's list of Departments.
     * @param department
     * @throws SQLException
     */
    public void updateDepartment(Department department) throws SQLException {
        departmentModel.updateDepartment(department);
        departments.forEach(dpt -> {
            if (dpt.getId() == department.getId()) {
                dpt.setName(department.getName());
                dpt.setManager(department.getManager());
            }
        });
    }

    /**
     * Returns a list of all Departments.
     * @return
     */
    public ObservableList<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets all Departments.
     * @param departments
     */
    public void setDepartments(ObservableList<Department> departments) {
        this.departments = departments;
    }

    /**
     * Get Department by name.
     * @param departmentName
     * @return
     */
    public Department getDepartment(String departmentName) {
        for (Department d : departments) {
            if (d.getName().equals(departmentName)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Get super Departments.
     * @return
     */
    public List<Department> getSuperDepartments() {
        return departmentModel.getSuperDepartments();
        //return null;
    }

    // _____ Messages _____

    /**
     * Adds a message to the database, and DataModel's list of messages. Assigns the message to
     * the specified ScreenBits.
     * @param user
     * @param newMessage
     * @param assignedScreenBits
     * @throws SQLException
     */
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        messageModel.addMessage(user, newMessage, assignedScreenBits);
        messages.add(newMessage);
        addMessageToScreenBits(newMessage, assignedScreenBits);
        bookScreenBitTimeTables(newMessage, assignedScreenBits);
    }

    /**
     * Adds a message to the ScreenBits' list of messages.
     * @param newMessage
     * @param assignedScreenBits
     */
    private void addMessageToScreenBits(Message newMessage, List<ScreenBit> assignedScreenBits) {
        assignedScreenBits.forEach(assignedScreenBit -> {
            screenBits.forEach(screenBit -> {
                if (assignedScreenBit.getId() == screenBit.getId()) {
                    screenBit.addMessage(newMessage);
                }
            });
        });
    }

    /**
     * Books the appropriate amount of time slots from the specified ScreenBits' time tables.
     * @param newMessage
     * @param assignedScreenBits
     */
    private void bookScreenBitTimeTables(Message newMessage, List<ScreenBit> assignedScreenBits) {
        assignedScreenBits.forEach(screenBit -> {
            screenBit.bookTimeSlots(newMessage);
        });
    }

    /**
     * Deletes a message from the database, from DataModel's list of messages, and from all associated ScreenBits.
     * @param message
     * @throws SQLException
     */
    public void deleteMessage(Message message) throws SQLException {
        messageModel.deleteMessage(message);
        messages.remove(message);
        deleteMessageFromScreenBits(message);
    }

    /**
     * Deletes a message from all associated ScreenBits.
     * @param message
     */
    private void deleteMessageFromScreenBits(Message message) {
        screenBits.forEach(screenBit -> {
            if (screenBit.getMessages().contains(message)) {
                screenBit.removeMessage(message);
            }
        });
    }

    /**
     * Updates a message in the database, in DataModel, and on all associated ScreenBits.
     * @param oldMessage
     * @param newMessage
     * @throws SQLException
     */
    public void updateMessage(Message oldMessage, Message newMessage) throws SQLException {
        messageModel.updateMessage(oldMessage, newMessage);
        messages.remove(oldMessage);
        messages.add(newMessage);
        updateMessageOnScreenBits(oldMessage, newMessage);
    }

    /**
     * Updates a message on all associated ScreenBits.
     * @param oldMessage
     * @param newMessage
     */
    private void updateMessageOnScreenBits(Message oldMessage, Message newMessage) {
        screenBits.forEach(s -> {
            s.removeMessage(oldMessage);
            s.addMessage(newMessage);
        });
    }

    /**
     * Get a specific Users messages.
     * @param user
     * @return
     */
    public List<Message> getUsersMessages(User user) {
        return messageModel.getUsersMessages(user);
    }

    /**
     * Get all messages.
     * @return
     */
    public ObservableList<Message> getMessages() {
        return messages;
    }

    /**
     * Set messages.
     * @param messages
     */
    public void setMessages(ObservableList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Load a specific ScreenBits messages.
     * @param screen
     * @throws SQLException
     */
    public void loadScreenBitsMessages(ScreenBit screen) throws SQLException {
        messageModel.loadScreenBitsMessages(screen);
    }

    // _____ Bugs _____

    /**
     * Adds a bug to the database.
     * @param newBug
     * @throws SQLException
     */
    public void addBug(Bug newBug) throws SQLException {
        getAllUnresolvedBugs().add(newBug);
        bugModel.addBug(newBug);
    }

    /**
     * Updates a bug in the database, and in DataModel.
     * @param oldBug
     * @param newBug
     * @throws SQLException
     */
    public void updateBug(Bug oldBug, Bug newBug) throws SQLException {
        bugModel.updateBug(oldBug, newBug);
        bugs.remove(oldBug);
        bugs.add(newBug);
    }

    /**
     * Deletes a bug from DataModel, and the database.
     * @param bug
     * @throws SQLException
     */
    public void deleteBug(Bug bug) throws SQLException {
        bugModel.deleteBug(bug);
        bugs.remove(bug);
    }

    /**
     * Get all bugs.
     * @return
     */
    public ObservableList<Bug> getAllBugs() {
        return bugs;
    }

    /**
     * Get all unresolved bugs.
     * @return
     */
    public ObservableList<Bug> getAllUnresolvedBugs() {
        return unresolvedBugs;
    }

    /**
     * Update all bugs.
     */
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

    // _____ Settings _____

    /**
     * Adds a setting to the database and DataModel.
     * @param settings
     * @throws SQLException
     */
    public void addSetting(Settings settings) throws SQLException {
        settingsModel.addSetting(settings);
        this.settings.add(settings);
    }

    /**
     * Get setting by type.
     * @param settings
     * @return
     */
    public Settings getSettingByType(Settings settings) {
        for (int i = 0; i < this.settings.size(); i++) {
            var setting = this.settings.get(i);
            if (setting.getType() == settings.getType()) {
                return setting;
            }
        }
        return null;
    }

    /**
     * Get setting by settingsType.
     * @param settingsType
     * @return
     */
    public Settings getSettingByType(SettingsType settingsType) {
        for (int i = 0; i < settings.size(); i++) {
            var setting = settings.get(i);
            if (setting.getType() == settingsType) {
                return setting;
            }
        }
        return null;
    }

    /**
     * Deletes a setting.
     * @param settings
     * @throws SQLException
     */
    public void deleteSetting(Settings settings) throws SQLException {
        settingsModel.deleteSetting(settings);
        this.settings.remove(settings);
    }

    /**
     * Updates a setting.
     * @param oldSettings
     * @param newSettings
     * @throws SQLException
     */
    public void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException {
        settingsModel.updateSetting(oldSettings, newSettings);
        settings.remove(oldSettings);
        settings.add(newSettings);
    }

    /**
     * Get all settings.
     * @return
     */
    public ObservableList<Settings> getSettings() {
        return settings;
    }

}
