package GUI.Controller.CrudControllers;

import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable {
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
    private JFXTextField txtPhoneNumber;
    @FXML
    private JFXTextField txtJobTitle;
    @FXML
    private JFXTextField txtDepartment;
    @FXML
    private JFXComboBox<Enum> chsRole;
    @FXML
    private JFXComboBox<Enum> chsSex;
    @FXML
    private JFXComboBox<Enum> chsSuperior;
    @FXML
    private ImageView image;


    private User oldUser;
    private UserModel userModel = UserModel.getInstance();
    private PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) throws SQLException {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !txtUsername.getText().isEmpty()
                 && !txtEmail.getText().isEmpty() && !chsRole.getSelectionModel().isEmpty()
                ) {

            int password = txtPassword.getText().isEmpty() ? oldUser.getPassword() : passwordManager.encrypt(txtPassword.getText());

            User newUser = new User(userModel.getAllUsers().size(), txtFirstname.getText(), txtLastname.getText(), txtUsername.getText()
                    , txtEmail.getText(), chsRole.getSelectionModel().getSelectedItem().ordinal(), password);
            userModel.updateUser(oldUser, newUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
            System.out.println("Edit saved!");
        } else System.out.println("Edit got wrecked! Not saved. Check all fields please.");
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

    public void setData(User user) {
        oldUser = user;
        txtFirstname.setText(user.getFirstName());
        txtLastname.setText(user.getLastName());
        txtUsername.setText(user.getUserName());
        txtEmail.setText(user.getEmail());
        chsRole.getSelectionModel().select(user.getUserRole());
    }

    public void handleSelectImage(){
        //TODO
    }
}
