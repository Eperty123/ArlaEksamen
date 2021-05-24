package BE;

import java.util.List;

public interface ISettingsCRUD {
    void addSetting(Settings settings);

    Settings getSettingByType(Settings settings);

    Settings getSettingByType(SettingsType settingsType);

    void deleteSetting(Settings settings);

    void updateSetting(Settings oldSettings, Settings newSettings);

    List<Settings> getSettings();
}
