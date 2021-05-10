package GUI.Controller.AdminControllers;

import BE.ScreenBit;
import BE.Searcher;
import BE.User;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(List<User> users){
        this.users = users;
        users.removeIf(user -> user.getAssignedScreen() != null);

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

    public void handleSave(ActionEvent actionEvent) {
        for (User u : lstScreenUsers.getItems()){
            u.setAssignedScreen(screenBit);
            ScreenModel.getInstance().assignScreenRights(u, screenBit);
        }

        screenBit.setAssignedUsers(lstScreenUsers.getItems());

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
        User selectedUser = lstScreenUsers.getSelectionModel().getSelectedItem();
        lstScreenUsers.getItems().remove(selectedUser);
        lstUsers.getItems().add(selectedUser);
    }


}
