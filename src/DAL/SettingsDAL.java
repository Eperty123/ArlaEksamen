package DAL;

import BE.Settings;
import BE.ISettingsCRUD;
import BE.SettingsType;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingsDAL implements ISettingsCRUD {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();

    @Override
    public List<Settings> getSettings() {
        List<Settings> settings = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Settings");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {
                settings.add(resultSetParser.getSetting(rs));
            }

            return settings;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to get all settings " +
//                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    @Override
    public void addSetting(Settings settings) throws SQLException {
        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Settings VALUES(?,?)");
            pSql.setInt(1, settings.getType().getValue());
            pSql.setString(2, settings.getAttribute());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to add the setting " +
//                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    @Override
    public Settings getSettingByType(Settings settings) {
        var allSettings = getSettings();
        for (int i = 0; i < allSettings.size(); i++) {
            var setting = allSettings.get(i);
            if (setting.getType() == settings.getType()) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public Settings getSettingByType(SettingsType settingsType) {
        var allSettings = getSettings();
        for (int i = 0; i < allSettings.size(); i++) {
            var setting = allSettings.get(i);
            if (setting.getType() == settingsType) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public void deleteSetting(Settings settings) throws SQLException {
        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("DELETE FROM Settings Where Id = ?");
            pSql.setInt(1, settings.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to delete the setting " +
//                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    @Override
    public void updateSetting(Settings oldSettings, Settings newSettings) throws SQLException {
        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("UPDATE Settings SET Attribute = ? Where Id = ?");
            pSql.setString(1, newSettings.getAttribute());
            pSql.setInt(2, oldSettings.getId());
            pSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
//            WarningController.createWarning("Oh no! Something went wrong when attempting to update the setting " +
//                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

}
