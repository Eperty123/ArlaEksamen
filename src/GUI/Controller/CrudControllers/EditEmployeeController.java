package GUI.Controller.CrudControllers;

import BE.Gender;
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

        chsSex.getItems().addAll(Gender.values());
    }

    public void setData(User user) {
        oldUser = user;
        txtFirstname.setText(user.getFirstName());
        txtLastname.setText(user.getLastName());
        txtJobTitle.setText(user.getTitle());
        txtEmail.setText(user.getEmail());
        txtPhoneNumber.setText(String.valueOf(user.getPhone()));
        chsSex.getSelectionModel().select(user.getGender());
        chsRole.getSelectionModel().select(user.getUserRole());

        txtUsername.setText(user.getUserName());
    }

    public void handleSelectImage(){
        //TODO
    }
}
