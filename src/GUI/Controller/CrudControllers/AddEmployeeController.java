package GUI.Controller.CrudControllers;

import BE.*;
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
import javafx.scene.image.ImageView;
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
    private JFXTextField txtPhoneNumber;
    @FXML
    private JFXTextField txtJobTitle;
    @FXML
    private JFXComboBox<Enum<UserType>> chsRole;
    @FXML
    private JFXComboBox<Enum<Gender>> chsSex;
    @FXML
    private JFXComboBox<Enum> chsSuperior;
    @FXML
    private ImageView image;

    private UserModel userModel = UserModel.getInstance();
    private PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) throws SQLException, IOException {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !txtUsername.getText().isEmpty()
                && !txtPassword.getText().isEmpty() && !txtEmail.getText().isEmpty() && !chsRole.getSelectionModel().isEmpty()
                ) {
            String firstName = txtFirstname.getText();
            String lastName = txtLastname.getText();
            String jobTitle = txtJobTitle.getText();
            String email = txtEmail.getText();
            int phone = Integer.parseInt(txtPhoneNumber.getText());
            Enum<Gender> sex = chsSex.getSelectionModel().getSelectedItem();
            Enum superior = chsSuperior.getSelectionModel().getSelectedItem();
            Enum<UserType> userRole = chsRole.getSelectionModel().getSelectedItem();
            String username = txtUsername.getText();
            int password = passwordManager.encrypt(txtPassword.getText());
            String imgPath = image.getImage().getUrl();

            //public User(String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {

            User newUser = new User(firstName,lastName,username,email,password,userRole.ordinal(),phone,sex,imgPath,jobTitle);

            // TODO Update
            Department department = new Department(444444, "Mock", newUser);
            // Add the new user.
            userModel.addUser(newUser, department);

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

        chsSex.getItems().addAll(Gender.values());
        chsSex.getSelectionModel().selectFirst();

        for (Enum e : chsRole.getItems()){
            System.out.println(e.name() + " - " + e.ordinal());
        }
    }

    public void handleSelectImage(){
        //TODO SELECT IMAGE
    }
}
