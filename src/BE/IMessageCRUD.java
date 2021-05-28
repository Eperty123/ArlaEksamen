package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IMessageCRUD {

    /**
     * Adds a message to the database, and creates a relation
     *
     * @param user               The user associated with the message.
     * @param newMessage         The message to put in to the database.
     * @param assignedScreenBits The list of screens to add the message to.
     * @throws SQLException
     */
    void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException;

    /**
     * Delete the specified message from the database.
     *
     * @param message The message to remove.
     * @throws SQLException
     */
    void deleteMessage(Message message) throws SQLException;

    /**
     * Update an existing message with a new one.
     *
     * @param oldMessage The current message to update.
     * @param newMessage The new message to update to.
     * @throws SQLException
     */
    void updateMessage(Message oldMessage, Message newMessage) throws SQLException;

    /**
     * Get a list of messages of the specified user.
     *
     * @param user The user to get messages of.
     * @return Returns a list of the user's messages.
     */
    List<Message> getUsersMessages(User user);

    /**
     * Get a list of messages of the specified user.
     *
     * @param user The user to get messages of.
     * @return Returns a list of the user's messages.
     */
    List<Message> getUsersMessages(String user);

    /**
     * Load the given screen's assigned messages.
     *
     * @param screen The screen to load messages of.
     * @throws SQLException
     */
    void loadScreenBitsMessages(ScreenBit screen) throws SQLException;

    /**
     * Get all messages in form of an ObservableList.
     *
     * @return
     */
    ObservableList<Message> getAllMessages();

    /**
     * Does messages exist in the database at all?
     *
     * @return Returns true if yes otherwise false.
     */
    boolean hasMessagesLoaded();
}
