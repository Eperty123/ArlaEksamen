package GUI.Model;

import BE.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public final class SuperModel {

    private static SuperModel instance;

    private UserModel userModel;
    private TitleModel titleModel;

    private ObservableList<User> users;
    private ObservableList<ScreenBit> screenBits;
    private ObservableList<Department> departments;
    private ObservableList<Message> messages;
    private ObservableList<String> titles;


    private SuperModel(){
        initialize();
    }

    private void initialize() {
        userModel = UserModel.getInstance();
        ScreenModel screenModel = ScreenModel.getInstance();
        DepartmentModel departmentModel = DepartmentModel.getInstance();
        MessageModel messageModel = MessageModel.getInstance();
        titleModel = TitleModel.getInstance();

        users = FXCollections.observableArrayList();
        screenBits = FXCollections.observableArrayList();
        departments = FXCollections.observableArrayList();
        messages = FXCollections.observableArrayList();
        titles = FXCollections.observableArrayList();

        users.addAll(userModel.getAllUsers());
        screenBits.addAll(screenModel.getAllScreenBits());
        departments.addAll(departmentModel.getAllDepartments());
        messages.addAll(messageModel.getAllMessages());
        titles.addAll(titleModel.getTitles());
    }

    public static SuperModel getInstance(){
        return instance == null ? instance = new SuperModel() : instance;
    }

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
    }

    public void addUser(User newUser, Department department) {
        if (users.stream().noneMatch(o -> o.getUserName().equals(newUser.getUserName()))) {
            userModel.addUser(newUser, department);
            users.add(newUser);
            // TODO add user to dpt
        }
    }

    public void updateUser(User oldUser, User newUser, Department oldDepartment, Department newDepartment) {
        userModel.updateUser(oldUser, newUser, oldDepartment, newDepartment);
        users.remove(oldUser);
        users.add(newUser);
        // TODO update dpt
    }

    public void deleteUser(User user) {
        userModel.deleteUser(user);
        users.remove(user);
    }

    public User getUser(int userId) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == userId)
                return user;
        }
        return null;
    }

    public User getUser(String userName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    public User getUser(String firstName, String lastName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(firstName) && user.getLastName().equals(lastName))
                return user;
        }
        return null;
    }

    public List<User> getAllUsersByRole(UserType role) {
        var filteredUsers = new ArrayList<User>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserRole() == role)
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

    public ObservableList<ScreenBit> getScreenBits() {
        return screenBits;
    }

    public void setScreenBits(ObservableList<ScreenBit> screenBits) {
        this.screenBits = screenBits;
    }

    public ObservableList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ObservableList<Department> departments) {
        this.departments = departments;
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ObservableList<Message> messages) {
        this.messages = messages;
    }

    public ObservableList<String> getTitles() {
        return titles;
    }

    public void setTitles(ObservableList<String> titles) {
        this.titles = titles;
    }
}
