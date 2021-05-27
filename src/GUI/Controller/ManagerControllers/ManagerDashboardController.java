package GUI.Controller.ManagerControllers;

import BE.ClockCalender;
import BE.SceneMover;
import BE.ScreenBit;
import BE.User;
import BLL.EmailManager;
import BLL.LoginManager;
import GUI.Controller.PopupControllers.ConfirmationDialog;
import GUI.Controller.PopupControllers.EScreenSelectDialog;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerDashboardController implements Initializable {
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblBar;
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private ImageView image;

    private User currentUser;
    private boolean isMaximized = false;
    private EmailManager emailManager = EmailManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();
        ClockCalender.initClock(dateTimeLabel);

        image.setImage(currentUser.getPhotoPath() == null ? new Image("/GUI/Resources/defaultPerson.png") : new Image(currentUser.getPhotoPath()));
        lblWelcome.setText("Welcome " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        lblBar.setText("Manager Dashboard - " + currentUser.getFirstName() + " " + currentUser.getLastName());
        checkEmailSettings();


        try {
            handleCreateMessage();
        } catch (IOException e) {
            e.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to read the Managers create message view." +
                    " Please try again. If the problem persists, please contact an IT-Administrator");

        }
    }

    /**
     * Check the email settings.
     */
    private void checkEmailSettings() {
        if (!emailManager.canSendEmail())
            WarningController.createWarning("The email for sending email notification for administrators is incorrect! Please contact an IT-Administrator about this!");
    }

    public void handleViewScreens() throws Exception {
        EScreenSelectDialog selectDialog = new EScreenSelectDialog(currentUser.getAssignedScreenBits());

        Optional<ScreenBit> result = selectDialog.showAndWait();

        if (result.isPresent()) {
            if (result.get() != null) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/ManagerViews/ManagerScreenView.fxml"));
                Parent root = fxmlLoader.load();
                ManagerScreenViewController controller = fxmlLoader.getController();
                controller.init(result.get());
                controller.setParentStage((Stage) this.root.getScene().getWindow());
                Scene scene = new Scene(root);
                BorderPane bp = (BorderPane) root.getScene().getRoot();
                SceneMover sceneMover = new SceneMover();
                sceneMover.move(stage, bp.getTop());

                stage.getIcons().addAll(
                        new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                        new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                        new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                        new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                        new Image("/GUI/Resources/AppIcons/icon64x64.png"));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setTitle("Manager screen view");
                stage.show();
                minimize();
            }
        }

    }

    public void handleCreateMessage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/ManagerViews/ManagerMessage.fxml"));
        Parent root = fxmlLoader.load();
        ManagerMessageController controller = fxmlLoader.getController();
        controller.setCurrentUser(currentUser);
        borderPane.setCenter(root);
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
