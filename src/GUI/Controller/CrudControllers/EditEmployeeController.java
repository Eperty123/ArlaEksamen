package GUI.Controller.CrudControllers;

import BE.Department;
import BE.Gender;
import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private JFXComboBox<Department> chsDepartment;
    @FXML
    private ImageView image;
    @FXML
    private JFXCheckBox hideEmailCheck;
    @FXML
    private JFXCheckBox hidePhoneCheck;


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
            String email = getEmail();
            int phone = getPhone();
            Enum<Gender> sex = chsSex.getSelectionModel().getSelectedItem();

            Enum<UserType> userRole = chsRole.getSelectionModel().getSelectedItem();
            String username = txtUsername.getText();
            int password = passwordManager.encrypt(txtPassword.getText());
            String imgPath = image.getImage().getUrl();

            //public User(String firstName, String lastName, String userName, String email, int password, int userRole, int phoneNumber, Enum gender, String photoPath, String title) {

            User newUser = new User(firstName,lastName,username,email,password,userRole.ordinal(),phone,sex,imgPath,jobTitle);


            Department oldDepartment = getUsersDepartment();
            Department newDepartment = chsDepartment.getValue();
            System.out.println(newDepartment.getName());

            userModel.updateUser(oldUser, newUser, oldDepartment, newDepartment);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
            System.out.println("Edit saved!");
        } else System.out.println("Edit got wrecked! Not saved. Check all fields please.");
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
        setDepartments();

        chsRole.getItems().addAll(UserType.values());
        chsRole.getSelectionModel().selectFirst();

        chsSex.getItems().addAll(Gender.values());
    }

    private void setDepartments() {
        chsDepartment.setItems(FXCollections.observableArrayList(DepartmentModel.getInstance().getAllDepartments()));
    }

    public void setData(User user) {
        oldUser = user;
        txtFirstname.setText(user.getFirstName());
        txtLastname.setText(user.getLastName());
        txtJobTitle.setText(user.getTitle());
        txtEmail.setText(setEmail(user));
        txtPhoneNumber.setText(setPhone(user));
        if(getUsersDepartment() == null){
            chsDepartment.setPromptText("Choose department");
        } else{
            chsDepartment.setValue(getUsersDepartment());
        }

        chsSex.getSelectionModel().select(user.getGender());
        chsRole.getSelectionModel().select(user.getUserRole());

        txtUsername.setText(user.getUserName());
    }

    private Department getUsersDepartment() {
        for(Department d : DepartmentModel.getInstance().getAllDepartments()) {
            if (d.getUsers().stream().anyMatch(o -> o.getUserName().equals(oldUser.getUserName()))) {
                return d;
            }
        }
        return null;
    }

    private String setPhone(User user) {
        if(user.getPhone() < 0){
            hidePhoneCheck.setSelected(true);
            return String.valueOf((user.getPhone() * -1));
        } else{
            hidePhoneCheck.setSelected(false);
            return String.valueOf(user.getPhone());
        }

    }

    private String setEmail(User user) {

        if(user.getEmail().charAt(0) == '@'){
            hideEmailCheck.setSelected(true);
            return user.getEmail().substring(1);
        } else{
            hideEmailCheck.setSelected(false);
            return user.getEmail();
        }
    }



    public void handleSelectImage(){
        //TODO
    }
}
