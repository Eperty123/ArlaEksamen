package GUI.Controller.CrudControllers;

import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import BLL.UserManager;
import DAL.UserDAL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
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
    @FXML
    private JFXComboBox chsScreen;

    private UserManager userManager = new UserManager();
    private PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !txtUsername.getText().isEmpty()
            && !txtPassword.getText().isEmpty() && !txtEmail.getText().isEmpty() && !chsRole.getSelectionModel().isEmpty()
            && !chsScreen.getSelectionModel().isEmpty()){

            User newUser = new User(userManager.getUsers().size(),txtFirstname.getText(), txtLastname.getText(),txtUsername.getText()
            , txtEmail.getText(),chsRole.getSelectionModel().getSelectedItem().ordinal(), passwordManager.encrypt(txtPassword.getText()));

            userManager.addUser(newUser);

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
        //TODO: få en liste af alle Screens der er tilgængelige, indtil da. MOCK DATA
        chsScreen.getItems().addAll("Screen 1", "Screen 2", "Screen 3", "Screen 4");
        chsScreen.getSelectionModel().selectFirst();

        chsRole.getItems().addAll(UserType.values());
        chsRole.getSelectionModel().selectFirst();
    }
}
