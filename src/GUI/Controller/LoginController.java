package GUI.Controller;

import BE.Timer;
import BE.User;
import BE.UserType;
import BLL.LoginManager;
import BLL.PasswordManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblError;
    @FXML
    private JFXButton btnLogin;

    private final LoginManager loginManager = new LoginManager();
    private final Timer timer = new Timer();

    private double xOffset = 0;
    private double yOffset = 0;

    public void login() throws SQLException, IOException {
        loginManager.attemptLogin(txtUsername.getText(),txtPassword.getText());
        User u = LoginManager.getCurrentUser();
        if (u != null){
            if (u.getUserRole() == UserType.Admin){
                Stage root1 = (Stage) root.getScene().getWindow();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/VIEW/AdminDashboard.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

                scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - xOffset);
                        stage.setY(event.getScreenY() - yOffset);
                        stage.setOpacity(0.8f);
                    }
                });

                scene.setOnMouseDragExited((event) -> {
                    stage.setOpacity(1.0f);
                });

                scene.setOnMouseReleased((event) -> {
                    stage.setOpacity(1.0f);
                });

                root1.close();
            }
            System.out.println("c:");
        }else{
            //TODO set error text til DP's password Timer
            timer.startTimer();
            lblError.setText(timer.getTxt().getText());

            timer.setNodeToDisable(btnLogin);

            txtPassword.clear();
            txtUsername.clear();
        }
    }


    public void loginWithEnter(KeyEvent keyEvent) throws SQLException, IOException {

        if (!btnLogin.isDisabled()) {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        }else{
            lblError.setText(timer.getTxt().getText());
        }
    }

    public void minimize(){
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }


    public void maximize(){

    }

    public void exit(){
        System.exit(0);
    }
}
