package GUI.Controller;

import BE.*;
import BLL.LoginManager;
import BLL.StageShower;
import GUI.Controller.PopupControllers.EScreenSelectDialog;
import GUI.Model.DataModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane topBar;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane lblPane;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private FontAwesomeIconView maximize;
    private boolean isMaximized = false;

    public FontAwesomeIconView getMaximize() {
        return maximize;
    }

    private final LoginManager loginManager = new LoginManager();
    private final Timer timer = new Timer();
    private final SceneMover sceneMover = new SceneMover();

    private final double xOffset = 0;
    private final double yOffset = 0;

    private int failedLoginAttempts = 0;
    private List<User> users;

    @FXML
    private void login() throws IOException {

        Boolean isCorrectLogin = loginManager.attemptLogin(txtUsername.getText(), txtPassword.getText(), users);

        if (!isCorrectLogin) {
            Label label = new Label();

            label.setTextAlignment(TextAlignment.CENTER);
            label.setTextFill(Paint.valueOf("RED"));
            label.setText("Wrong Username or Password!");

            new Timeline(new KeyFrame(
                    javafx.util.Duration.seconds(5),
                    ae -> label.setVisible(false)))
                    .play();

            txtPassword.clear();
            txtUsername.clear();

            lblPane.setCenter(label);
        }
        User u = LoginManager.getCurrentUser();
        if (u != null) {
            Stage root1 = (Stage) root.getScene().getWindow();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            StageShower stageShower = new StageShower();

            Scene scene = null;
            if (u.getUserRole() == UserType.Admin) {
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminViews/AdminDashboard.fxml"));
                Parent parent = fxmlLoader.load();
                stage.setTitle("Admin dashboard");
                BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
                stageShower.showScene(stage, parent, sceneMover, borderPane.getTop());
                root1.close();
            } else if (u.getUserRole() == UserType.Manager) {
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/ManagerViews/ManagerDashboard.fxml"));
                stage.setTitle("Manager Dashboard");
                Parent parent = fxmlLoader.load();
                BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
                stageShower.showScene(stage, parent, sceneMover, borderPane.getTop());
                root1.close();
            } else if (u.getUserRole() == UserType.HR) {
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/HRViews/HRDashboard.fxml"));
                stage.setTitle("HR Dashboard");
                Parent parent = fxmlLoader.load();
                BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
                stageShower.showScene(stage, parent, sceneMover, borderPane.getTop());
                root1.close();
            } else {
                EScreenSelectDialog selectDialog = new EScreenSelectDialog(u.getAssignedScreenBits());

                Optional<ScreenBit> result = selectDialog.showAndWait();

                if (result.isPresent()) {
                    if (result.get() != null) {
                        stage.setTitle("Employee Screen");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/GUI/View/EmployeeScreen.fxml"));
                        Parent parent = loader.load();
                        EmployeeScreenController controller = loader.getController();
                        controller.init(result.get());

                        BorderPane bp = (BorderPane) parent;
                        sceneMover.move(stage, bp.getTop());
                        stageShower.showScene(stage, parent, sceneMover, bp.getTop());
                        root1.close();
                    }
                }
            }
        } else {
            failedLoginAttempts++;
            if (failedLoginAttempts % 3 == 0)
                startTimer();
        }
    }

    /**
     * Adds the label of the Timer Class, uses addNodeToDisable
     * and Timer's .startTimer method
     * also clears the password and username field
     */
    private void startTimer() {
        timer.startTimer();
        timer.setTimeoutDuration(Duration.ofSeconds(timer.getTimeoutDuration().getSeconds()));
        lblPane.getChildren().clear();
        Label label = timer.getTimerLabel();

        label.setTextAlignment(TextAlignment.CENTER);
        label.setTextFill(Paint.valueOf("RED"));
        lblPane.setCenter(label);

        timer.addNodeToDisable(btnLogin);

        txtPassword.clear();
        txtUsername.clear();
    }

    public void loginWithEnter(KeyEvent keyEvent) throws SQLException, IOException {
        if (!btnLogin.isDisabled()) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        }
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = new ArrayList<>();
        users.addAll(DataModel.getInstance().getUsers());

    }
}
