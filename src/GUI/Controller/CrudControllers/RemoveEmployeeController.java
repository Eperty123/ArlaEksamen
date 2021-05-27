package GUI.Controller.CrudControllers;

import BE.User;
import GUI.Model.DataModel;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RemoveEmployeeController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    private User user;
    private final UserModel userModel = UserModel.getInstance();

    public void setData(User user){
        this.user = user;
        Text text1 = new Text(user.getFirstName() + " " + user.getLastName());
        textArea.setText("Are you sure you want to delete " + text1.getText().toUpperCase() + " " +
                "from the employee list?\n\nThis action is not reversible!");
    }

    public void handleJa(ActionEvent actionEvent) {

        DataModel.getInstance().deleteUser(user);


        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleNej(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }


}
