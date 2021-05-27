package GUI.Controller.PopupControllers;

import BE.Settings;
import BE.SettingsType;
import GUI.Model.SettingsModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtMessageUpdate;
    @FXML
    private JFXTextField txtEmployeeCard;
    @FXML
    private JFXTextField txtPasswordTimeout;

    private SettingsModel settingsModel = SettingsModel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    private void initialize() {
        settingsModel.getAllSettings().addListener((ListChangeListener<Settings>) c -> {
            //setData();
        });
    }

    /**
     * Set the appropriate field values found from settings.
     */
    private void setData() {
        var allSettings = settingsModel.getAllSettings();
        if (allSettings.size() > 0) {
            for (int i = 0; i < allSettings.size(); i++) {
                var setting = allSettings.get(i);
                setValueForSettingsType(setting);
            }
        }
    }

    public void handleSave() {
        updateSettings();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Get the appropriate control value by the settings type.
     *
     * @param settingsType The setting type to get value of.
     */
    private String getValueForSettingsType(SettingsType settingsType) {
        String result = null;
        switch (settingsType) {
            case MESSAGE_CHECK_FREQUENCY -> result = txtMessageUpdate.getText();
            case CARD_OPEN_DURATION -> result = txtEmployeeCard.getText();
            case WRONG_PASS_FREEZE_DURATION -> result = txtPasswordTimeout.getText();
        }
        return (result == null || result.isEmpty()) ? "" : result;
    }

    /**
     * Set the appropriate control value by the settings type.
     *
     * @param settings The settings to get value of.
     */
    private void setValueForSettingsType(Settings settings) {
        switch (settings.getType()) {
            case MESSAGE_CHECK_FREQUENCY -> txtMessageUpdate.setText(settings.getAttribute());
            case CARD_OPEN_DURATION -> txtEmployeeCard.setText(settings.getAttribute());
            case WRONG_PASS_FREEZE_DURATION -> txtPasswordTimeout.setText(settings.getAttribute());
        }
    }

    /**
     * Update all the settings.
     */
    private void updateSettings() {
        var allSettings = settingsModel.getAllSettings();

        if (allSettings.size() > 0) {
            for (int i = 0; i < allSettings.size(); i++) {
                var setting = allSettings.get(i);
                var updatedValue = getValueForSettingsType(setting.getType());
                setting.setAttribute(updatedValue);
                settingsModel.updateSetting(setting, setting);
            }
        }

        // Create new settings...
        if (settingsModel.getSettingByType(SettingsType.MESSAGE_CHECK_FREQUENCY) == null)
            settingsModel.addSetting(new Settings(SettingsType.MESSAGE_CHECK_FREQUENCY, getValueForSettingsType(SettingsType.MESSAGE_CHECK_FREQUENCY)));

        if (settingsModel.getSettingByType(SettingsType.CARD_OPEN_DURATION) == null)
            settingsModel.addSetting(new Settings(SettingsType.CARD_OPEN_DURATION, getValueForSettingsType(SettingsType.CARD_OPEN_DURATION)));

        if (settingsModel.getSettingByType(SettingsType.WRONG_PASS_FREEZE_DURATION) == null)
            settingsModel.addSetting(new Settings(SettingsType.WRONG_PASS_FREEZE_DURATION, getValueForSettingsType(SettingsType.WRONG_PASS_FREEZE_DURATION)));
    }

    /**
     * Create a warning dialog with the specified message.
     *
     * @return Returns the created WarningController instance.
     */
    public static SettingsController openSettings() {
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
