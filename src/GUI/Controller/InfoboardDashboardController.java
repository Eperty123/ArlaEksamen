package GUI.Controller;

import BE.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InfoboardDashboardController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblBar;
    @FXML
    private Label lblTime;

    private boolean isMaximized = false;

    public BorderPane getRootBorderPane() {
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClockCalender.initClock(lblTime);
        lblBar.setText("Infoboard");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/View/Infoboard.fxml"));
        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InfoboardController controller = fxmlLoader.getController();
        borderPane.setCenter(root);
    }

    @FXML
    private void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleMaximize(MouseEvent mouseEvent) {
        isMaximized = !isMaximized;
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setMaximized(isMaximized);
    }

    @FXML
    private void handleClose(MouseEvent mouseEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
}
