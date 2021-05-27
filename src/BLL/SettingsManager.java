package BLL;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import DAL.SettingsDAL;

import java.util.List;

public class SettingsManager implements ISettingsCRUD {
    private final SettingsDAL settingsDAL = new SettingsDAL();

    @Override
    public void addSetting(Settings settings) {
        this.settingsDAL.addSetting(settings);
    }

    @Override
    public Settings getSettingByType(Settings settings) {
        return this.settingsDAL.getSettingByType(settings);
    }

    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        return this.settingsDAL.getSettingByType(settingsType);
    }

    @Override
    public void deleteSetting(Settings settings) {
        this.settingsDAL.deleteSetting(settings);
    }

    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) {
        this.settingsDAL.updateSetting(oldSettings, newSettings);
    }


    @Override
    public List<Settings> getSettings() {
        return this.settingsDAL.getSettings();
    }
}
