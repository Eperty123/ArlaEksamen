package GUI.Model;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import BLL.SettingsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SettingsModel implements ISettingsCRUD {
    private SettingsManager settingsManager = new SettingsManager();
    private ObservableList<Settings> settings = FXCollections.observableArrayList();
    private static SettingsModel instance;

    public SettingsModel() {
        updateAllSettings();
    }

    @Override
    public void addSetting(Settings settings) {
        settingsManager.addSetting(settings);
        updateAllSettings();
    }

    @Override
    public Settings getSettingByType(Settings settings) {
        for (int i = 0; i < this.settings.size(); i++) {
            var setting = this.settings.get(i);
            if (setting.getType().equals(settings.getType())) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        for (int i = 0; i < settings.size(); i++) {
            var setting = settings.get(i);
            if (setting.getType().equals(settingsType)) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public void deleteSetting(Settings settings) {
        settingsManager.deleteSetting(settings);
        updateAllSettings();
    }

    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) {
        settingsManager.updateSetting(oldSettings, newSettings);
        updateAllSettings();
    }

    @Override
    public List<Settings> getSettings() {
        return settingsManager.getSettings();
    }

    public void updateAllSettings() {
        settings.setAll(getSettings());
    }

    public ObservableList<Settings> getAllSettings() {
        return settings;
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
            System.out.println(String.format("%s singleton was reset.", getClass().getSimpleName()));
        }
    }

    public static SettingsModel getInstance() {
        return instance == null ? instance = new SettingsModel() : instance;
    }
}
