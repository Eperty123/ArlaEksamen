package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WarningController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextArea textArea;

    public void setText(String text) {
        textArea.setText(text);
    }

    public void handleOk() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Create a warning dialog with the specified message.
     *
     * @param message The message to show.
     * @return Returns the created WarningController instance.
     */
    public static WarningController createWarning(String message) {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(WarningController.class.getResource("/GUI/View/PopUpViews/Warning.fxml"));

        try {
            stage1.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        WarningController warningController = fxmlLoader.getController();
        warningController.setText(message);
        stage1.initStyle(StageStyle.TRANSPARENT);
        stage1.show();
        stage1.setAlwaysOnTop(true);
        return warningController;
    }
}
