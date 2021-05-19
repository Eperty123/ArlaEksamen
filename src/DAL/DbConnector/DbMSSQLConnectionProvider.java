/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DbConnector;

import GUI.Controller.PopupControllers.WarningController;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author: Carlo De Leon
 * Version: 1.1.1
 */
public class DbMSSQLConnectionProvider implements IDbConnectionProvider {

    protected String host;
    protected String user;
    protected String password;
    protected String database;
    protected int port = 1433;
    private SQLServerDataSource ds;
    private static DbMSSQLConnectionProvider instance;

    protected String databasePath;
    protected Properties databaseFileProperties;

    public DbMSSQLConnectionProvider() {
    }

    public DbMSSQLConnectionProvider(String settingsFile) {
        loadSettingsFile(settingsFile);
        connect();
    }

    public static DbMSSQLConnectionProvider getInstance() {
        if (instance == null) instance = new DbMSSQLConnectionProvider();
        return instance;
    }

    /**
     * Connect to the database.
     */
    public Connection connect() {
        ds = new SQLServerDataSource();
        ds.setServerName(getHost());
        ds.setDatabaseName(getDatabase());
        ds.setUser(getUser());
        ds.setPassword(getPassword());
        ds.setPortNumber(getPort());

        System.out.println(String.format("[%s]: Successfully connected to database: %s!", this.getClass().getSimpleName(), getDatabase()));
        return getConnection();
    }

    /**
     * Reconnect to the database.
     */
    @Override
    public Connection reconnect() {
        try {
            if (ds == null || ds.getConnection() != null && ds.getConnection().isClosed())
                return connect();
            return ds.getConnection();
        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong trying to reconnect to the Database." +
                    " Please try again. If the problem persists, please contact an IT-Administrator");

        }
        return null;
    }


    /**
     * Get the current connection.
     *
     * @return The current Connection instance.
     */
    @Override
    public Connection getConnection() {
        return reconnect();
    }

    /**
     * Get the database name.
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
     * Gets the user.
     *
     * @return The user.
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     * Get the password.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the port.
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
     * Set the host.
     *
     * @param host The host.
     */
    @Override
    public void setHost(String host) {
        if (host.isEmpty()) return;
        this.host = host;
    }

    /**
     * Set the user for the database.
     *
     * @param user The user for the database.
     */
    @Override
    public void setUser(String user) {
        if (user.isEmpty()) return;
        this.user = user;
    }

    /**
     * Set the password for the database's user.
     *
     * @param password The password for the user.
     */
    @Override
    public void setPassword(String password) {
        if (password.isEmpty()) return;
        this.password = password;
    }

    /**
     * Set the port for the database.
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
