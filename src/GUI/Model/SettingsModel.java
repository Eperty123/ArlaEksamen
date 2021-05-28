package GUI.Model;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import BLL.SettingsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SettingsModel implements ISettingsCRUD {
    private final SettingsManager settingsManager = new SettingsManager();
    private final ObservableList<Settings> settings = FXCollections.observableArrayList();
    private static SettingsModel instance;

    public SettingsModel() {
        updateAllSettings();
    }

    @Override
    public void addSetting(Settings settings) {
        settingsManager.addSetting(settings);
    }

    @Override
    public Settings getSettingByType(Settings settings) {
        for (int i = 0; i < this.settings.size(); i++) {
            var setting = this.settings.get(i);
            if (setting.getType() == settings.getType()) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        for (int i = 0; i < settings.size(); i++) {
            var setting = settings.get(i);
            if (setting.getType() == settingsType) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public void deleteSetting(Settings settings) {
        settingsManager.deleteSetting(settings);
    }

    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) {
        settingsManager.updateSetting(oldSettings, newSettings);
    }

    @Override
    public List<Settings> getSettings() {
        return settingsManager.getSettings();
    }

    public void updateAllSettings() {
        settings.setAll(getSettings());
    }

    public List<Settings> getAllSettings() {
        return settingsManager.getSettings();
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }

    public static SettingsModel getInstance() {
        return instance == null ? instance = new SettingsModel() : instance;
    }
}
