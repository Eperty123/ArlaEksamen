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
    private boolean messagesLoaded;

    public MessageManager() {
        initialize();
    }


    private void initialize() {
        messagesLoaded = messageDAL.getAllMessages() != null;
    }

    /**
     * Adds a message to the database, and creates a relation
     *
     * @param user
     * @param newMessage
     * @param assignedScreenBits
     * @throws SQLException
     */
    @Override
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    /**
     * Retrieves all messages from the database, related to the specified user.
     *
     * @param user
     * @return
     */
    @Override
    public void deleteMessage(Message message) throws SQLException {
        messageDAL.deleteMessage(message);
    }

    @Override
    public void updateMessage(Message oldMessage, Message newMessage) throws SQLException {
        messageDAL.updateMessage(oldMessage, newMessage);
    }

    @Override
    public List<Message> getUsersMessages(User user) {
        return messageDAL.getUsersMessages(user);
    }

    /**
     * Retrieves all messages from the database, related to the specified user name.
     *
     * @param user
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

    @Override
    public ObservableList<Message> getAllMessages() {
        return FXCollections.observableArrayList(messageDAL.getAllMessages());
    }

    @Override
    public boolean hasMessagesLoaded() {
        return messagesLoaded;
    }
}