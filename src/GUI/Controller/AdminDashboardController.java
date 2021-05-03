package GUI.Controller;

import BE.User;
import BLL.LoginManager;
import BLL.UserManager;
import GUI.Controller.PopupControllers.ConfirmationController;
import GUI.Model.UserModel;
import com.mysql.cj.protocol.x.XProtocolRow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
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
    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginManager.getCurrentUser();

        lblWelcome.setText("Velkommen " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        lblBar.setText("Admin Dashboard - " + currentUser.getFirstName() + " " + currentUser.getLastName());
    }

    public void handleUserManagement() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminManagement.fxml"));

        borderPane.setCenter(fxmlLoader.load());
    }

    public void handleScreenManagement() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/AdminScreenManagement.fxml"));

        borderPane.setCenter(fxmlLoader.load());
    }

    public void minimize() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    public void exit() {
        System.exit(0);
    }

    private boolean confirmation() throws IOException {
        Stage confirmationStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/VIEW/PopUpViews/Confirmation.fxml"));

        Parent root = (Parent) loader.load();
        ConfirmationController confirmationController = loader.getController();

        Scene confirmationScene = new Scene(root);

        confirmationScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        confirmationScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmationStage.setX(event.getScreenX() - xOffset);
                confirmationStage.setY(event.getScreenY() - yOffset);
                confirmationStage.setOpacity(0.8f);
            }
        });

        confirmationScene.setOnMouseDragExited((event) -> {
            confirmationStage.setOpacity(1.0f);
        });

        confirmationScene.setOnMouseReleased((event) -> {
            confirmationStage.setOpacity(1.0f);
        });


        confirmationStage.initStyle(StageStyle.UNDECORATED);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();

        return confirmationController.isConfirmed();
    }

    public void Logout(ActionEvent actionEvent) throws IOException {

        if (confirmation()) {
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
