package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import BE.Searcher;
import BE.User;
import BE.UserType;
import GUI.Model.ScreenModel;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditScreenController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView<User> lstUsers;
    @FXML
    private JFXTextField txtUserField;
    @FXML
    private ListView<User> lstScreenUsers;
    @FXML
    private Label lblScreenName;

    private ScreenBit screenBit;
    private List<User> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(List<User> users){
        this.users = users;

        this.users.removeIf(user -> user.getUserRole() == UserType.Admin ||
                screenBit.getAssignedUsers().stream().anyMatch(o -> o.getUserName().equalsIgnoreCase(user.getUserName())));

        lstUsers.getItems().addAll(users);
    }

    public void setScreen(ScreenBit screenBit){
        this.screenBit = screenBit;
        lblScreenName.setText(screenBit.getName());
        if (screenBit.getAssignedUsers() !=null) {
            lstScreenUsers.getItems().addAll(screenBit.getAssignedUsers());
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    // TODO update to handle List
    public void handleSave(ActionEvent actionEvent) {
        for (User u : lstScreenUsers.getItems()){
            if (!u.getAssignedScreen().contains(screenBit)) {
                u.addScreenAssignment(screenBit);
                ScreenModel.getInstance().assignScreenBitRights(u, screenBit);
            }
        }

        for (User u  : lstUsers.getItems()){
            if (u.getAssignedScreen().contains(screenBit)){
                u.removeScreenAssignment(screenBit);
                ScreenModel.getInstance().removeScreenBitRights(u,screenBit);
            }
            screenBit.removeUser(u);
        }

        screenBit.setAssignedUsers(lstScreenUsers.getItems());

        System.out.println(screenBit.getAssignedUsers());

        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void handleSearch() {
        if (!txtUserField.getText().isEmpty() || txtUserField.getText() != null && !lstUsers.getItems().isEmpty()) {
            lstUsers.setItems(Searcher.search(users, txtUserField.getText()));
        }
    }

    public void handleAddUser(MouseEvent mouseEvent) {
        User selectedUser = lstUsers.getSelectionModel().getSelectedItem();
        lstUsers.getItems().remove(selectedUser);
        lstScreenUsers.getItems().add(selectedUser);
    }

    public void handleRemoveUser(MouseEvent mouseEvent) {
        // TODO this does not remove screen rights, when saved
        User selectedUser = lstScreenUsers.getSelectionModel().getSelectedItem();
        lstScreenUsers.getItems().remove(selectedUser);
        lstUsers.getItems().add(selectedUser);

        System.out.println(lstScreenUsers.getItems());
    }


}
