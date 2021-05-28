package DAL.DbConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author: Carlo De Leon
 * Version: 1.1.1
 */
public interface IDbConnectionProvider {

    /**
     * Get the active connection to the database.
     *
     * @return The current active connection.
     */
    Connection getConnection() throws SQLException;

    /**
     * Get the name of the database.
     *
     * @return The database name.
     */
    String getDatabase();

    /**
     * Get the host of the database.
     *
     * @return The host for the database.
     */
    String getHost();

    /**
     * Get the database user.
     *
     * @return The database user.
     */
    String getUser();

    /**
     * Get the database user password.
     *
     * @return The database user password.
     */
    String getPassword();

    /**
     * Get the database port.
     *
     * @return The database port.
     */
    int getPort();

    /**
     * Connect to the database.
     */
    Connection connect() throws SQLException;

    /**
     * Reconnect to the database.
     */
    Connection reconnect() throws SQLException;

    /**
     * Set the database name.
     *
     * @param database The name of the database name.
     */
    void setDatabase(String database);

    /**
     * Set the host.
     *
     * @param host Set the host.
     */
    void setHost(String host);

    /**
     * Set the database user.
     *
     * @param user the database user.
     */
    void setUser(String user);

    /**
     * Set the database password.
     *
     * @param password The database user password.
     */
    void setPassword(String password);

    /**
     * Set the database port.
     *
     * @param port The port to use.
     */
    void setPort(int port);

    /**
     * Load the specified settings file from path.
     */
    void loadSettingsFile(String path) throws IOException;

    /**
     * Set the specified database settings file.
     *
     * @param path
     */
    void setSettingsFile(String path) throws IOException;

    /**
     * Get the path for the database settings file.
     *
     * @return
     */
    String getSettingsFile();

    /**
     * Get the database settings properties.
     *
     * @return
     */
    Properties getDatabaseProperties();
}
