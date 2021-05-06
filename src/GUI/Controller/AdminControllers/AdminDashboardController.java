package GUI.Controller.AdminControllers;

import BE.User;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationController;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblBar;
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane borderPane;

    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();

        lblWelcome.setText("Welcome " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        lblBar.setText("Admin Dashboard - " + currentUser.getFirstName() + " " + currentUser.getLastName());
        try {
            handleUserManagement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUserManagement() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminViews/AdminManagement.fxml"));

        borderPane.setCenter(fxmlLoader.load());
    }

    public void handleScreenManagement() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminViews/AdminScreenManagement.fxml"));

        borderPane.setCenter(fxmlLoader.load());
    }

    public void minimize() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    public void exit() {
        System.exit(0);
    }

    public void Logout(ActionEvent actionEvent) throws IOException {
        ConfirmationDialog confirmation = new ConfirmationDialog("Are you sure you want to logout of the application?");

        Optional<Boolean> result = confirmation.showAndWait();

        if (result.isPresent()){
            if (result.get()){
                Stage root1 = (Stage) root.getScene().getWindow();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/Login.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                root1.close();
            }
        }
    }
}
