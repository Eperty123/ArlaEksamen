package GUI.Controller;

import BE.*;
import BLL.LoginManager;
import GUI.Model.ScreenModel;
import GUI.Model.UserModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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

    private double xOffset = 0;
    private double yOffset = 0;

    private int failedLoginAttempts = 0;

    @FXML
    private void login() throws SQLException, IOException {
        loginManager.attemptLogin(txtUsername.getText(), txtPassword.getText());
        User u = LoginManager.getCurrentUser();
        if (u != null) {
            Stage root1 = (Stage) root.getScene().getWindow();

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();

            if (u.getUserRole() == UserType.Admin) {
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/AdminViews/AdminDashboard.fxml"));
                stage.setTitle("Admin dashboard");
            } else if(u.getUserRole() == UserType.Manager) {
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/ManagerViews/ManagerDashboard.fxml"));
                stage.setTitle("Manager Dashboard");
            }else{
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/EmployeeScreen.fxml"));
                stage.setTitle("Employee Screen");
            }

            Scene scene = new Scene(fxmlLoader.load());
            stage.getIcons().addAll(
                    new Image("/GUI/Resources/AppIcons/icon16x16.png"),
                    new Image("/GUI/Resources/AppIcons/icon24x24.png"),
                    new Image("/GUI/Resources/AppIcons/icon32x32.png"),
                    new Image("/GUI/Resources/AppIcons/icon48x48.png"),
                    new Image("/GUI/Resources/AppIcons/icon64x64.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            BorderPane borderPane = (BorderPane) scene.getRoot().getChildrenUnmodifiable().get(0);
            sceneMover.move(stage,borderPane.getTop());

            root1.close();
        } else {
            failedLoginAttempts++;
            if(failedLoginAttempts%3==0)
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
        timer.setTimeoutDuration(Duration.ofSeconds(timer.getTimeoutDuration().getSeconds()*2));
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
    }
}
