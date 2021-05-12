package GUI.Controller.CrudControllers;

import BE.ScreenBit;
import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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

    public void handleSave(ActionEvent actionEvent) throws SQLException, IOException {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !txtUsername.getText().isEmpty()
                && !txtPassword.getText().isEmpty() && !txtEmail.getText().isEmpty() && !chsRole.getSelectionModel().isEmpty()
                ) {

            User newUser = new User(txtFirstname.getText(), txtLastname.getText(), txtUsername.getText()
                    , txtEmail.getText(), chsRole.getSelectionModel().getSelectedItem().ordinal(), passwordManager.encrypt(txtPassword.getText()));

            // Add the new user.
            userModel.addUser(newUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }else{
            Stage warning = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/VIEW/popupviews/Warning.fxml"));
            warning.setTitle("Warning!");
            Parent root = (Parent) loader.load();

            WarningController warningController = loader.getController();
            warningController.setText("Warning! You've not entered some crucial information about the employee.\n\n" +
                    "Please check if all fields are filled in");

            Scene warningScene = new Scene(root);

            warning.initStyle(StageStyle.UNDECORATED);
            warning.setScene(warningScene);
            warning.show();
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

        for (Enum e : chsRole.getItems()){
            System.out.println(e.name() + " - " + e.ordinal());
        }
    }
}
