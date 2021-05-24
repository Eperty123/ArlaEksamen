package GUI.Controller.PopupControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SettingsController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtMessageUpdate;
    @FXML
    private JFXTextField txtEmployeeCard;
    @FXML
    private JFXTextField txtPasswordTimeout;
    @FXML
    private JFXComboBox<String> cmbTimeFormat;

    private void setData(){        
        //TODO
    }

    public void handleSave() {
        //TODO: Save settings
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleCancel(){
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Create a warning dialog with the specified message.
     *
     * @return Returns the created WarningController instance.
     */
    public static SettingsController OpenSettings() {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SettingsController.class.getResource("/GUI/View/PopUpViews/Settings.fxml"));

        try {
            stage1.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        SettingsController settingsController = fxmlLoader.getController();
        settingsController.setData();
        stage1.initStyle(StageStyle.TRANSPARENT);
        stage1.show();
        stage1.setAlwaysOnTop(true);
        return settingsController;
    }
}
