package GUI.Controller.CrudControllers;

import BE.ScreenBit;
import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtFirstname;
    @FXML
    private JFXTextField txtLastname;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtPassword;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXComboBox<Enum> chsRole;


    private UserModel userModel = UserModel.getInstance();
    private PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !txtUsername.getText().isEmpty()
                && !txtPassword.getText().isEmpty() && !txtEmail.getText().isEmpty() && !chsRole.getSelectionModel().isEmpty()
                ) {

            User newUser = new User(txtFirstname.getText(), txtLastname.getText(), txtUsername.getText()
                    , txtEmail.getText(), chsRole.getSelectionModel().getSelectedItem().ordinal(), passwordManager.encrypt(txtPassword.getText()));

            // Add the new user.
            userModel.addUser(newUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chsRole.getItems().addAll(UserType.values());
        chsRole.getSelectionModel().selectFirst();
    }
}
