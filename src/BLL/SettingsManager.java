package BLL;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import DAL.SettingsDAL;

import java.sql.SQLException;
import java.util.List;

public class SettingsManager implements ISettingsCRUD {
    private final SettingsDAL settingsDAL = new SettingsDAL();

    /**
     * Add the specified settings to the database.
     *
     * @param settings The settings instance to add.
     * @throws SQLException
     */
    @Override
    public void addSetting(Settings settings) throws SQLException {
        settingsDAL.addSetting(settings);
    }

    /**
     * Get a setting by its type.
     *
     * @param settings The settings to get.
     * @return Returns the settings associated with the type.
     */
    @Override
    public Settings getSettingByType(Settings settings) {
        return settingsDAL.getSettingByType(settings);
    }

    /**
     * Get a setting by its type.
     *
     * @param settingsType The SettingsType to compare with.
     * @return Returns the settings associated with the type.
     */
    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        return settingsDAL.getSettingByType(settingsType);
    }

    /**
     * Delete the specified settings from the database.
     *
     * @param settings The settings to delete.
     * @throws SQLException
     */
    @Override
    public void deleteSetting(Settings settings) throws SQLException {
        settingsDAL.deleteSetting(settings);
    }

    /**
     * Update the current settings with a new one.
     *
     * @param oldSettings The current settings to update.
     * @param newSettings The new settings to update to.
     * @throws SQLException
     */
    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException {
        settingsDAL.updateSetting(oldSettings, newSettings);
    }

    /**
     * Get a list of settings.
     *
     * @return Returns a list of settings.
     */
    @Override
    public List<Settings> getSettings() {
        return settingsDAL.getSettings();
    }
}
