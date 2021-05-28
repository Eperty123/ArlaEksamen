package BLL;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.MessageDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class MessageManager {

    private final MessageDAL messageDAL;

    public MessageManager() {
        this.messageDAL = new MessageDAL();
    }

    /**
     * Adds a message to the database, and creates a relation
     * @param user
     * @param newMessage
     * @param assignedScreenBits
     * @throws SQLException
     */
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        this.messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    /**
     * Deletes a message from the database.
     * @param message
     */
    public void deleteMessage(Message message) {
        this.messageDAL.deleteMessage(message);
    }

    /**
     * Updates message information in the database.
     * @param oldMessage
     * @param newMessage
     */
    public void updateMessage(Message oldMessage, Message newMessage) {
        this.messageDAL.updateMessage(oldMessage, newMessage);
    }

    /**
     * Retrieves all messages from the database, related to the specified user.
     * @param user
     * @return
     */
    public List<Message> getUsersMessages(User user) {
        return this.messageDAL.getUsersMessages(user);
    }

    /**
     * Retrieves all messages from the database, related to the specified user name.
     * @param user
     * @return
     */
    public List<Message> getUsersMessages(String user) {
        return this.messageDAL.getUsersMessages(user);
    }

    /**
     * Load messages from the database related to the specified ScreenBit.
     * @param screen
     */
    public void loadScreenBitsMessages(ScreenBit screen) {
        this.messageDAL.loadScreenBitsMessages(screen);
    }

    /**
     * Retrieves all messages from the database.
     * @return
     */
    public ObservableList<Message> getAllMessages() {
        return FXCollections.observableArrayList(this.messageDAL.getAllMessages());
    }


}