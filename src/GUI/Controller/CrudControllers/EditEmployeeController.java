package GUI.Controller.CrudControllers;

import BE.Department;
import BE.Gender;
import BE.User;
import BE.UserType;
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
    private JFXComboBox<Enum> chsRole;
    @FXML
    private JFXComboBox<Enum> chsSex;
    @FXML
    private JFXComboBox<Department> chsDepartment;
    @FXML
    private ImageView image;
    @FXML
    private JFXCheckBox hideEmailCheck;
    @FXML
    private JFXCheckBox hidePhoneCheck;
    @FXML
    private JFXComboBox<String> chsTitle;


    private User oldUser;
    private final PasswordManager passwordManager = new PasswordManager();

    public void handleSave(ActionEvent actionEvent) {
        if (!txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() && !chsTitle.getSelectionModel().getSelectedItem().isEmpty()
                && !txtEmail.getText().isEmpty() && !txtPhoneNumber.getText().isEmpty() && chsSex.getSelectionModel().getSelectedItem() != null
                && chsDepartment.getSelectionModel() != null && chsRole.getSelectionModel().getSelectedItem() != null && !txtUsername.getText().isEmpty()
                && !txtPassword.getText().isEmpty()) {
            int id = oldUser.getId();
            String firstName = txtFirstname.getText();
            String lastName = txtLastname.getText();
            String jobTitle = chsTitle.getValue();
            String email = getEmail();
            int phone = getPhone();
            Enum<Gender> sex = chsSex.getSelectionModel().getSelectedItem();

            Enum<UserType> userRole = chsRole.getSelectionModel().getSelectedItem();
            String username = txtUsername.getText();
            int password = passwordManager.encrypt(txtPassword.getText());
            String imgPath = image.getImage().getUrl().isEmpty() ? new Image("/GUI/Resources/defaultPerson.png").getUrl() : image.getImage().getUrl();


            User newUser = new User(id, firstName, lastName, username, email, password, userRole.ordinal(), phone, sex, imgPath, jobTitle);


            Department newDepartment = chsDepartment.getValue();


            DataModel.getInstance().updateUser(newUser, newDepartment);


            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        } else {
            WarningController.createWarning("Warning! You've not entered some crucial information about the employee.\n\n" +
                    "Please check if all fields are filled in");
        };
    }

    private int getPhone() {
        if (hidePhoneCheck.isSelected()) {
            return Integer.parseInt(txtPhoneNumber.getText()) * -1;
        } else {
            return Integer.parseInt(txtPhoneNumber.getText());
        }
    }

    private String getEmail() {
        if (hideEmailCheck.isSelected()) {
            return "@" + txtEmail.getText();
        } else {
            return txtEmail.getText();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDepartments();
        setTitles();

        chsRole.getItems().addAll(UserType.values());
        chsRole.getSelectionModel().selectFirst();

        chsSex.getItems().addAll(Gender.values());
    }

    private void setTitles() {
        chsTitle.setItems(FXCollections.observableArrayList(TitleModel.getInstance().getTitles()));
    }

    private void setDepartments() {
        chsDepartment.setItems(FXCollections.observableArrayList(DataModel.getInstance().getDepartments()));
    }

    public void setData(User user) {
        oldUser = user;
        txtFirstname.setText(user.getFirstName());
        txtLastname.setText(user.getLastName());
        chsTitle.setValue(oldUser.getTitle());
        txtEmail.setText(setEmail(user));
        txtPhoneNumber.setText(setPhone(user));
        if (getUsersDepartment() == null) {
            chsDepartment.setPromptText("Choose department");
        } else {
            chsDepartment.setValue(getUsersDepartment());
        }

        image.setImage(user.getPhotoPath() == null ? new Image("/GUI/Resources/defaultPerson.png") : new Image(user.getPhotoPath()));
        chsSex.getSelectionModel().select(user.getGender());
        chsRole.getSelectionModel().select(user.getUserRole());

        txtUsername.setText(user.getUserName());
    }

    private Department getUsersDepartment() {
        for (Department d : DataModel.getInstance().getDepartments()) {
            if (d.getUsers().stream().anyMatch(o -> o.getUserName().equals(oldUser.getUserName()))) {
                return d;
            }
        }
        return null;
    }

    private String setPhone(User user) {
        if (user.getPhone() < 0) {
            hidePhoneCheck.setSelected(true);
            return String.valueOf((user.getPhone() * -1));
        } else {
            hidePhoneCheck.setSelected(false);
            return String.valueOf(user.getPhone());
        }

    }

    private String setEmail(User user) {

        if (user.getEmail().charAt(0) == '@') {
            hideEmailCheck.setSelected(true);
            return user.getEmail().substring(1);
        } else {
            hideEmailCheck.setSelected(false);
            return user.getEmail();
        }
    }


    public void handleSelectImage() {
        AddEmployeeController.handleFileChooser(image);

    }
}
