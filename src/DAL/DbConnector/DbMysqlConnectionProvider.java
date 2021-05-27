package DAL.DbConnector;

import GUI.Controller.PopupControllers.WarningController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author: Carlo De Leon
 * Version: 1.1.1
 */
public class DbMysqlConnectionProvider implements IDbConnectionProvider {

    protected String host;
    protected String user;
    protected String password;
    protected String database;
    protected int port = 3306;

    protected String databasePath;
    protected Properties databaseFileProperties;
    protected Connection connection;
    private static DbMysqlConnectionProvider instance;

    public DbMysqlConnectionProvider() {
    }

    public DbMysqlConnectionProvider(String settingsFile) {
        loadSettingsFile(settingsFile);
        connect();
    }

    public static DbMysqlConnectionProvider getInstance() {
        if (instance == null) instance = new DbMysqlConnectionProvider();
        return instance;
    }

    /**
     * Connect to the database.
     */
    @Override
    public Connection connect() {
        try {
            // Connect to the database.
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), getDatabase()), getUser(), getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Connection reconnect() {
        try {
            if (connection == null || connection.isClosed())
                return connect();
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong trying to reconnect to the Database." +
                    " Please try again. If the problem persists, please contact an IT-Administrator");
        }
        return null;
    }


    /**
     * Get the current connection.
     *
     * @return The current connection.
     */
    @Override
    public Connection getConnection() {
        return reconnect();
    }

    /**
     * Get the databse name.
     *
     * @return The database name.
     */
    @Override
    public String getDatabase() {
        return database;
    }

    /**
     * Get the host.
     *
     * @return The host.
     */
    @Override
    public String getHost() {
        return host;
    }

    /**
     * Get the database's user.
     *
     * @return The database's user.
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     * Get the database user password.
     *
     * @return The database user's password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the port of the database.
     *
     * @return The port.
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * Set the database name.
     *
     * @param database The database name to connect to.
     */
    @Override
    public void setDatabase(String database) {
        if (database.isEmpty()) return;
        this.database = database;
    }

    /**
     * Set the host for the database.
     *
     * @param host The host for the database.
     */
    @Override
    public void setHost(String host) {
        if (host.isEmpty()) return;
        this.host = host;
    }

    /**
     * Set the database user.
     *
     * @param user The database user.
     */
    @Override
    public void setUser(String user) {
        if (user.isEmpty()) return;
        this.user = user;
    }

    /**
     * Set the database user's password.
     *
     * @param password The database user's password.
     */
    @Override
    public void setPassword(String password) {
        if (password.isEmpty()) return;
        this.password = password;
    }

    /**
     * Set the database port.
     *
     * @param port The database port.
     */
    @Override
    public void setPort(int port) {
        if (port <= 0) return;
        this.port = port;
    }

    /**
     * Load the specified database file from the given path.
     *
     * @param path The path to the database settings file.
     */
    @Override
    public void loadSettingsFile(String path) {
        if (!path.isEmpty()) {
            try {
                databasePath = path;
                databaseFileProperties = new Properties();
                databaseFileProperties.load(new FileInputStream(new File(path)));

                setHost(databaseFileProperties.getProperty("Server"));
                setDatabase(databaseFileProperties.getProperty("Database"));
                setUser(databaseFileProperties.getProperty("User"));
                setPassword(password = databaseFileProperties.getProperty("Password"));
                setPort(Integer.parseInt(databaseFileProperties.getProperty("Port")));

            } catch (IOException e) {
                e.printStackTrace();
                WarningController.createWarning("Oh no! Something went wrong trying to read the Database settings. " +
                    "It might be corrupted or lost. Please try again. If the problem persists, please contact an IT-Administrator");
            }
        }
    }

    /**
     * Set the database settings file path.
     *
     * @param path The path to the database settings file.
     */
    @Override
    public void setSettingsFile(String path) {
        if (!path.isEmpty()) {
            databasePath = path;
            loadSettingsFile(path);
        }
    }

    /**
     * Get the path to the database settings file.
     *
     * @return
     */
    @Override
    public String getSettingsFile() {
        return databasePath;
    }

    /**
     * Get the database settings properties.
     *
     * @return
     */
    @Override
    public Properties getDatabaseProperties() {
        return databaseFileProperties;
    }
}
