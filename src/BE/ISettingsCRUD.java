package BE;

import java.sql.SQLException;
import java.util.List;

public interface ISettingsCRUD {

    /**
     * Add the specified settings to the database.
     * @param settings The settings instance to add.
     * @throws SQLException
     */
    void addSetting(Settings settings) throws SQLException;

    /**
     * Get a setting by its type.
     * @param settings The settings to get.
     * @return Returns the settings associated with the type.
     */
    Settings getSettingByType(Settings settings);

    /**
     * Get a setting by its type.
     * @param settingsType The SettingsType to compare with.
     * @return Returns the settings associated with the type.
     */
    Settings getSettingByType(SettingsType settingsType);

    /**
     * Delete the specified settings from the database.
     * @param settings
     * @throws SQLException
     */
    void deleteSetting(Settings settings) throws SQLException;

    /**
     * Update the current settings with a new one.
     * @param oldSettings The current settings to update.
     * @param newSettings The new settings to update to.
     * @throws SQLException
     */
    void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException;

    /**
     * Get a list of settings.
     * @return Returns a list of settings.
     */
    List<Settings> getSettings();
}
