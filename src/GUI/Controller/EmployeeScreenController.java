package GUI.Controller;

import BE.User;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.WarningController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class EmployeeScreenController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblBar;

    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();

        if (!currentUser.getAssignedScreen().isEmpty()) {

            lblBar.setText("Employee Screen - " + currentUser.getAssignedScreen().get(0).getName() + " - " + currentUser.getFirstName() + " " + currentUser.getLastName());
            try {
                setScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblBar.setText("Employee Screen - NONE Contact admin - "  + currentUser.getFirstName() + " " + currentUser.getLastName());
            try {
                displayNoScreenWarning();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void displayNoScreenWarning() throws IOException {
        String text = "You have not been assigned a screen yet. \n\n" +
                      "Please, contact an IT administrator as soon as possible, to resolve this problem.";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/PopUpViews/Warning.fxml"));
        Parent root = (Parent) loader.load();

        WarningController warningController = loader.getController();
        warningController.setText(text);
        Stage stage = new Stage();

        Scene scene = new Scene(root);

        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    private void setScreen() throws Exception {
        StageBuilder stageBuilder = new StageBuilder();
        Node screen = stageBuilder.makeStage(currentUser.getAssignedScreen().get(0).getScreenInfo());
        stageBuilder.getRootController().lockPanes();
        borderPane.setCenter(screen);
    }

    public void handleLogout() throws IOException {
        ConfirmationDialog confirmation = new ConfirmationDialog("Are you sure you want to logout of the application?");

        Optional<Boolean> result = confirmation.showAndWait();

        if (result.isPresent()) {
            if (result.get()) {
                Stage root1 = (Stage) borderPane.getScene().getWindow();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/Login.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                LoginManager.setCurrentUser(null);
                root1.close();
            }
        }
    }
}
