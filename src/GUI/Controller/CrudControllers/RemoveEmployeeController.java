package GUI.Controller.CrudControllers;

import BE.User;
import BLL.UserManager;
import DAL.UserDAL;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class RemoveEmployeeController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    private User user;
    private UserManager userManager = new UserManager();

    public void setData(User user){
        this.user = user;
        Text text1 = new Text(user.getFirstName() + " " + user.getLastName());
        textArea.setText("Are you sure you want to delete " + text1.getText().toUpperCase() + " " +
                "from the employee list?\n\nThis action is not reversible!");
    }

    public void handleJa(ActionEvent actionEvent) {
        userManager.deleteUser(user.getId());

        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleNej(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }


}
