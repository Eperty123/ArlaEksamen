package BLL;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import DAL.SettingsDAL;

import java.sql.SQLException;
import java.util.List;

public class SettingsManager implements ISettingsCRUD {
    private final SettingsDAL settingsDAL = new SettingsDAL();

    @Override
    public void addSetting(Settings settings) throws SQLException {
        settingsDAL.addSetting(settings);
    }

    @Override
    public Settings getSettingByType(Settings settings) {
        return settingsDAL.getSettingByType(settings);
    }

    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        return settingsDAL.getSettingByType(settingsType);
    }

    @Override
    public void deleteSetting(Settings settings) throws SQLException {
        settingsDAL.deleteSetting(settings);
    }

    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException {
        settingsDAL.updateSetting(oldSettings, newSettings);
    }


    @Override
    public List<Settings> getSettings() {
        return settingsDAL.getSettings();
    }
}
