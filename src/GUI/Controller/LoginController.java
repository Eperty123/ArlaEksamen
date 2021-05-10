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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
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

    public FontAwesomeIconView getMaximize() {
        return maximize;
    }

    private final LoginManager loginManager = new LoginManager();
    private final Timer timer = new Timer();
    private final SceneMover sceneMover = new SceneMover();

    private double xOffset = 0;
    private double yOffset = 0;

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
            } else {
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/EmployeeScreen.fxml"));
                stage.setTitle("Employee Screen");
            }

            Scene scene = new Scene(fxmlLoader.load());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            BorderPane borderPane = (BorderPane) scene.getRoot().getChildrenUnmodifiable().get(0);
            sceneMover.move(stage,borderPane.getTop());

            root1.close();
        } else {
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

    }

    public void exit() {
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ScreenBit> screens = ScreenModel.getInstance().getAllScreenBits();
        List<User> users = UserModel.getInstance().getAllUsers();

        for (ScreenBit s : screens) {
            for (User u : users) {
                if(s.getAssignedUsers().contains(u)){
                    u.addScreenAssignment(s);
                }
            }
        }
    }
}
