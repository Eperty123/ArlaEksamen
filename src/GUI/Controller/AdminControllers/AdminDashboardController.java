package GUI.Controller.AdminControllers;

import BE.ClockCalender;
import BE.SceneMover;
import BE.User;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.BugModel;
import GUI.Model.MessageModel;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private JFXButton btnSelectAll;
    @FXML
    private Label dateTimeLabel;

    private User currentUser;
    private boolean isMaximized = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        ClockCalender.initClock(dateTimeLabel);

        lblWelcome.setText("Welcome " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        lblBar.setText("Admin Dashboard - " + currentUser.getFirstName() + " " + currentUser.getLastName());
        try {
            handleUserManagement();
        } catch (IOException e) {
            e.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to load the admins user management view." +
                    " It might be corrupted or lost.");
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

    public void handleBugManagement() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminViews/AdminBugReport.fxml"));
        borderPane.setCenter(fxmlLoader.load());
    }

    public void handleCreateMessage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminViews/AdminMessage.fxml"));
        borderPane.setCenter(fxmlLoader.load());
    }

    public void minimize() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maximize() {
        isMaximized = !isMaximized;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setMaximized(isMaximized);
    }

    public void exit() {
        System.exit(0);
    }

    public void Logout(ActionEvent actionEvent) throws IOException {
        ConfirmationDialog confirmation = new ConfirmationDialog("Are you sure you want to logout of the application?");

        Optional<Boolean> result = confirmation.showAndWait();

        if (result.isPresent()) {
            if (result.get()) {
                SceneMover sceneMover = new SceneMover();

                // Reset the singleton instance so we don't leave any cache behind.
                UserModel.getInstance().resetSingleton();
                ScreenModel.getInstance().resetSingleton();
                BugModel.getInstance().resetSingleton();
                MessageModel.getInstance().resetSingleton();

                Stage root1 = (Stage) root.getScene().getWindow();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/Login.fxml"));

                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Login");
                stage.getIcons().addAll(
                        new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                        new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                        new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                        new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                        new Image("/GUI/Resources/AppIcons/icon64x64.png"));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                BorderPane borderPane = (BorderPane) stage.getScene().getRoot();

                sceneMover.move(stage, borderPane.getTop());

                LoginManager.setCurrentUser(null);
                root1.close();
            }
        }
    }
}
