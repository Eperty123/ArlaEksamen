package DAL.DbConnector;

import BE.DatabaseType;
import GUI.Controller.PopupControllers.WarningController;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Author: Carlo De Leon
 * Version: 1.1.1
 */
public class DbConnectionHandler {
    private IDbConnectionProvider database;
    private static DbConnectionHandler instance;

    private final static String database_settings_path = "src/Resources/database.settings";


    public DbConnectionHandler() {
        loadDatabaseSettings();
    }

    /**
     * Load and parse the database settings.
     */
    private void loadDatabaseSettings() {
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream(database_settings_path));

            // Get the database type and determine which of the connection providers to use.
            DatabaseType databaseType = DatabaseType.valueOf(databaseProperties.getProperty("DatabaseType"));

            switch (databaseType) {
                case MySQL -> database = new DbMysqlConnectionProvider();
                case MSSQL -> database = new DbMSSQLConnectionProvider();
            }

            // Now load database settings.
            database.setHost(databaseProperties.getProperty("Server"));
            database.setDatabase(databaseProperties.getProperty("Database"));
            database.setUser(databaseProperties.getProperty("User"));
            database.setPassword(databaseProperties.getProperty("Password"));
            database.setPort(Integer.parseInt(databaseProperties.getProperty("Port")));
        } catch (IOException e) {
            e.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to read the Database settings." +
                    " Please try again. If the problem persists, please contact an IT-Administrator");
        }
    }

    /// Instance Methods

    /**
     * Get or establish a new connection to the database.
     *
     * @return Returns the current active Connection to the database.
     */
    public Connection getConnection() {
        return database.getConnection();
    }

    /// Static Methods

    /**
     * Get the current active singleton instance. If null a new instance will be created.
     *
     * @return Returns the current active singleton instance.
     */
    public static DbConnectionHandler getInstance() {
        if (instance == null) instance = new DbConnectionHandler();
        return instance;
    }
}
