package BLL;

import BE.IMessageCRUD;
import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.MessageDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class MessageManager implements IMessageCRUD {

    private final MessageDAL messageDAL = new MessageDAL();

    /**
     * Adds a message to the database, and creates a relation
     *
     * @param user               The user associated with the message.
     * @param newMessage         The message to put in to the database.
     * @param assignedScreenBits The list of screens to add the message to.
     * @throws SQLException
     */
    @Override
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    /**
     * Retrieves all messages from the database, related to the specified user.
     *
     * @param message The message to remove.
     */
    @Override
    public void deleteMessage(Message message) throws SQLException {
        messageDAL.deleteMessage(message);
    }

    /**
     * Update an existing message with a new one.
     *
     * @param oldMessage The current message to update.
     * @param newMessage The new message to update to.
     * @throws SQLException
     */
    @Override
    public void updateMessage(Message oldMessage, Message newMessage) throws SQLException {
        messageDAL.updateMessage(oldMessage, newMessage);
    }

    /**
     * Get a list of messages of the specified user.
     *
     * @param user The user to get messages of.
     * @return Returns a list of the user's messages.
     */
    @Override
    public List<Message> getUsersMessages(User user) {
        return messageDAL.getUsersMessages(user);
    }

    /**
     * Retrieves all messages from the database, related to the specified user name.
     *
     * @param user The user to get messages of.
     * @return
     */
    @Override
    public List<Message> getUsersMessages(String user) {
        return messageDAL.getUsersMessages(user);
    }

    /**
     * Retrieves all messages from the database.
     *
     * @return
     */
    @Override
    public void loadScreenBitsMessages(ScreenBit screen) throws SQLException {
        messageDAL.loadScreenBitsMessages(screen);
    }

    /**
     * Get all messages in form of an ObservableList.
     *
     * @return
     */
    @Override
    public ObservableList<Message> getAllMessages() {
        return FXCollections.observableArrayList(messageDAL.getAllMessages());
    }
}