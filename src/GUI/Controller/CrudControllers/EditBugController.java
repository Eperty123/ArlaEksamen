package GUI.Controller.CrudControllers;

import BE.Bug;
import BE.User;
import BE.UserType;
import BLL.PasswordManager;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.BugModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditBugController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXComboBox<User> chsAdmin;

    private Bug selectedBug;
    private final BugModel bugModel = BugModel.getInstance();
    private final PasswordManager passwordManager = new PasswordManager();

    public void setData(Bug bug) {
        selectedBug = bug;
    }

    public void handleSave(ActionEvent actionEvent) throws IOException {
        if (!chsAdmin.getSelectionModel().isEmpty()) {

            // Create a new Bug instance with updated values.
            Bug newBug = selectedBug;
            newBug.setId(selectedBug.getId());
            newBug.setDescription(selectedBug.getDescription());
            newBug.setAdminId(chsAdmin.getSelectionModel().getSelectedItem().getId());
            newBug.setReferencedScreen(selectedBug.getReferencedScreen());

            // Update the selected Bug report.
            bugModel.updateBug(selectedBug, newBug);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        } else {
            WarningController.createWarning("Warning! You have not selected which admin should take responsibility for this bug!\n\n" +
                    "Please select an admin in the choice box!");
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> admins = new ArrayList<>();

        for (User u : DataModel.getInstance().getUsers()) {
            if (u.getUserRole() == UserType.Admin) {
                admins.add(u);
            }
        }

        chsAdmin.getItems().addAll(admins);
        chsAdmin.getSelectionModel().selectFirst();
    }
}
