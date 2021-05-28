package BE;

import java.sql.SQLException;
import java.util.List;

public interface ISettingsCRUD {
    void addSetting(Settings settings) throws SQLException;

    Settings getSettingByType(Settings settings);

    Settings getSettingByType(SettingsType settingsType);

    void deleteSetting(Settings settings) throws SQLException;

    void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException;

    List<Settings> getSettings();
}
