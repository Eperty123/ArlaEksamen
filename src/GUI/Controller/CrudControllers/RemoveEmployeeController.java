package GUI.Controller.CrudControllers;

import BE.User;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DataModel;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RemoveEmployeeController {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private AnchorPane root;

    private User user;

    public void setData(User user){
        this.user = user;
        Text text1 = new Text(user.getFirstName() + " " + user.getLastName());
        textArea.setText("Are you sure you want to delete " + text1.getText().toUpperCase() + " " +
                "from the employee list?\n\nThis action is not reversible!");
    }

    public void handleJa(ActionEvent actionEvent) {

        try {
            DataModel.getInstance().deleteUser(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a user " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }


        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleNej(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }


}
