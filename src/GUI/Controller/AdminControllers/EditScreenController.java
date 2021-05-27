package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import BE.Searcher;
import BE.User;
import BE.UserType;
import GUI.Model.DataModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private final List<User> usersToAdd = new ArrayList<>();
    private final List<User> usersToDelete = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(List<User> users){
        this.users = new ArrayList<>(users);

        this.users.removeIf(user -> user.getUserRole() == UserType.Admin ||
                screenBit.getAssignedUsers().stream().anyMatch(o -> o.getUserName().equalsIgnoreCase(user.getUserName())));

        lstUsers.getItems().addAll(this.users);
    }

    public void setScreen(ScreenBit screenBit){
        this.screenBit = screenBit;
        lblScreenName.setText(screenBit.getName());
        if (screenBit.getAssignedUsers() !=null) {
            lstScreenUsers.getItems().addAll(screenBit.getAssignedUsers());
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        usersToAdd.clear();
        usersToDelete.clear();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }


    public void handleSave(ActionEvent actionEvent) {
        if(usersToDelete != null){
            DataModel.getInstance().removeScreenBitRights(usersToDelete, screenBit);
        }
        if(usersToAdd != null){
            DataModel.getInstance().assignScreenBitRights(usersToAdd, screenBit);
        }



        setData(DataModel.getInstance().getUsers());
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void handleSearch() {
        if (!txtUserField.getText().isEmpty() || txtUserField.getText() != null && !lstUsers.getItems().isEmpty()) {
            lstUsers.setItems(Searcher.searchUsers(users, txtUserField.getText()));
        }
    }

    public void handleAddUser(MouseEvent mouseEvent) {
        User selectedUser = lstUsers.getSelectionModel().getSelectedItem();
        usersToAdd.add(selectedUser);
        lstUsers.getItems().remove(selectedUser);
        lstScreenUsers.getItems().add(selectedUser);
    }

    public void handleRemoveUser(MouseEvent mouseEvent) {
        User selectedUser = lstScreenUsers.getSelectionModel().getSelectedItem();
        usersToDelete.add(selectedUser);
        lstUsers.getItems().add(selectedUser);
        lstScreenUsers.getItems().remove(selectedUser);
    }


}
