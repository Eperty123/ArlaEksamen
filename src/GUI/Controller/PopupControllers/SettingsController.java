package GUI.Controller.PopupControllers;

import BE.Settings;
import BE.SettingsType;
import GUI.Model.SettingsModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    private SettingsModel settingsModel = SettingsModel.getInstance();

    private void setData() {
        //TODO
    }

    public void handleSave() {
        //TODO: Save settings
        updateSettings();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    private void updateSettings() {
        var allSettings = settingsModel.getAllSettings();

        if (allSettings.size() > 0) {
            for (int i = 0; i < allSettings.size(); i++) {
                var setting = allSettings.get(i);
                switch (setting.getType()) {
                    case MESSAGE_CHECK_FREQUENCY -> setting.setAttribute(txtMessageUpdate.getText());
                    case CARD_OPEN_DURATION -> setting.setAttribute(txtEmployeeCard.getText());
                    case TIME_FORMAT -> setting.setAttribute(cmbTimeFormat.getSelectionModel().getSelectedItem());
                    case WRONG_PASS_FREEZE_DURATION -> setting.setAttribute(txtPasswordTimeout.getText());
                }
                settingsModel.updateSetting(setting, setting);
            }
        } else {
            // Create new settings...
            if (settingsModel.getSettingByType(SettingsType.MESSAGE_CHECK_FREQUENCY) == null)
                settingsModel.addSetting(new Settings(SettingsType.MESSAGE_CHECK_FREQUENCY, txtMessageUpdate.getText()));

            else if (settingsModel.getSettingByType(SettingsType.CARD_OPEN_DURATION) == null)
                settingsModel.addSetting(new Settings(SettingsType.CARD_OPEN_DURATION, txtEmployeeCard.getText()));

            else if (settingsModel.getSettingByType(SettingsType.TIME_FORMAT) == null)
                settingsModel.addSetting(new Settings(SettingsType.TIME_FORMAT, cmbTimeFormat.getSelectionModel().getSelectedItem()));

            else if (settingsModel.getSettingByType(SettingsType.WRONG_PASS_FREEZE_DURATION) == null)
                settingsModel.addSetting(new Settings(SettingsType.WRONG_PASS_FREEZE_DURATION, txtPasswordTimeout.getText()));
        }
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
