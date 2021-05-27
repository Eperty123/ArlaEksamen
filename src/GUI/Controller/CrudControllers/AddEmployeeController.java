package GUI.Controller.CrudControllers;

import BE.*;
import BLL.PasswordManager;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DataModel;
import GUI.Model.DepartmentModel;
import GUI.Model.TitleModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private JFXComboBox<Department> chsDepartment;
    @FXML
    private ImageView image;
    @FXML
    private JFXCheckBox hidePhoneCheck;
    @FXML
    private JFXCheckBox hideEmailCheck;
    @FXML
    private JFXComboBox<String> chsTitle;

    private final UserModel userModel = UserModel.getInstance();
    private final PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) {

        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !chsTitle.getSelectionModel().getSelectedItem().isEmpty()
                && !txtEmail.getText().isEmpty() && !txtPhoneNumber.getText().isEmpty() && chsSex.getSelectionModel().getSelectedItem() != null
                && chsDepartment.getSelectionModel() != null && chsRole.getSelectionModel().getSelectedItem() != null && !txtUsername.getText().isEmpty()
                && !txtPassword.getText().isEmpty()) {
            String firstName = txtFirstname.getText();
            String lastName = txtLastname.getText();
            String jobTitle = chsTitle.getValue();
            String email = getEmail();
            int phone = getPhone();
            Enum<Gender> sex = chsSex.getSelectionModel().getSelectedItem();
            Department department = chsDepartment.getSelectionModel().getSelectedItem();
            Enum<UserType> userRole = chsRole.getSelectionModel().getSelectedItem();
            String username = txtUsername.getText();
            int password = passwordManager.encrypt(txtPassword.getText());
            String imgPath = image.getImage().getUrl().isEmpty() ? new Image("/GUI/Resources/defaultPerson.png").getUrl() : image.getImage().getUrl();

            //public User(String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {

            User newUser = new User(firstName,lastName,username,email,password,userRole.ordinal(),phone,sex,imgPath,jobTitle);

            // Add the new user.
            DataModel.getInstance().addUser(newUser, department);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }else{
            WarningController.createWarning("Warning! You've not entered some crucial information about the employee.\n\n" +
                    "Please check if all fields are filled in");
        }
    }

    private int getPhone() {
        if(hidePhoneCheck.isSelected()){
            return Integer.parseInt(txtPhoneNumber.getText()) * -1;
        } else{
            return Integer.parseInt(txtPhoneNumber.getText());
        }

    }

    private String getEmail() {
        if(hideEmailCheck.isSelected()){
            return "@" + txtEmail.getText();
        } else{
            return txtEmail.getText();
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
        setDepartments();
        setTitles();
    }

    private void setTitles() {
        chsTitle.setItems(FXCollections.observableArrayList(DataModel.getInstance().getTitles()));
    }

    private void setDepartments() {
        chsDepartment.setItems(FXCollections.observableArrayList(DataModel.getInstance().getDepartments()));
    }

    public void handleSelectImage() {
        handleFileChooser(image);

    }

    static void handleFileChooser(ImageView image) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Resource File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = chooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            image.setImage(selectedImage);
        }
    }
}
