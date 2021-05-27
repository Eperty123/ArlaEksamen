package GUI.Controller.ManagerControllers;

import BE.*;
import BLL.EmailManager;
import BLL.LoginManager;
import BLL.StageShower;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private final StageShower stageShower = new StageShower();

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

                BorderPane bp = (BorderPane) root.getScene().getRoot();
                SceneMover sceneMover = new SceneMover();

                stageShower.showScene(stage,root,sceneMover,bp.getTop());

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
                Stage root1 = (Stage) root.getScene().getWindow();
                SceneMover sceneMover = new SceneMover();
                // Reset the singleton instance so we don't leave any cache behind.
                UserModel.getInstance().resetSingleton();
                ScreenModel.getInstance().resetSingleton();

                stageShower.handleLogout(root1,sceneMover);
            }
        }
    }
}
