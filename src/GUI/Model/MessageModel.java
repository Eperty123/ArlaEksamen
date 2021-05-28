package GUI.Model;

import BE.IMessageCRUD;
import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public final class MessageModel implements IMessageCRUD {

    private static MessageModel instance;
    private final MessageManager messageManager;


    private MessageModel() {
        messageManager = new MessageManager();
    }

    /**
     * Adds a message to the database, and assigns it to the ScreenBit(s) provided.
     *
     * @param user
     * @param newMessage
     * @param assignedScreenBits
     * @throws SQLException
     */
    @Override
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        messageManager.addMessage(user, newMessage, assignedScreenBits);
    }

    @Override
    public void deleteMessage(Message message) throws SQLException {
        messageManager.deleteMessage(message);
    }

    @Override
    public void updateMessage(Message oldMessage, Message newMessage) throws SQLException {
        messageManager.updateMessage(oldMessage, newMessage);
    }

    @Override
    public List<Message> getUsersMessages(User user) {
        return messageManager.getUsersMessages(user);
    }

    @Override
    public List<Message> getUsersMessages(String user) {
        return messageManager.getUsersMessages(user);
    }

    @Override
    public ObservableList<Message> getAllMessages() {
        return messageManager.getAllMessages();
    }

    @Override
    public boolean hasMessagesLoaded() {
        return messageManager.hasMessagesLoaded();
    }

    @Override
    public void loadScreenBitsMessages(ScreenBit screen) throws SQLException {
        messageManager.loadScreenBitsMessages(screen);
    }

    public List<Message> getAllUserMessages(String userName) {
        return messageManager.getUsersMessages(userName);
    }

    /**
     * Loads a ScreenBit's messages to it, from the database.
     * <p>
     * Get the singleton instance.
     *
     * @return Returns a singleton instance of this class.
     */

    public static MessageModel getInstance() {
        return instance == null ? instance = new MessageModel() : instance;
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }
}
