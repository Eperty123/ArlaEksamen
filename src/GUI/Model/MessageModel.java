package GUI.Model;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public final class MessageModel {

    private static MessageModel instance;
    private final MessageManager messageManager;


    private MessageModel(){
        messageManager = new MessageManager();
    }

    /**
     * Get the singleton instance.
     *
     * @return Returns a singleton instance of this class.
     */
    public static MessageModel getInstance() {
        return instance == null ? instance = new MessageModel() : instance;
    }

    /**
     * Adds a message to the database, and assigns it to the ScreenBit(s) provided.
     * @param user
     * @param newMessage
     * @param assignedScreenBits
     * @throws SQLException
     */
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        messageManager.addMessage(user, newMessage, assignedScreenBits);
    }

    /**
     * Deletes a message in the database, referencing message id.
     * @param message
     */
    public void deleteMessage(Message message){
        messageManager.deleteMessage(message);
    }

    /**
     * Updates a message in the database, referencing message id.
     * @param oldMessage
     * @param newMessage
     */
    public void updateMessage(Message oldMessage, Message newMessage) {
        messageManager.updateMessage(oldMessage, newMessage);
    }

    /**
     * Retrieves all messages related to a specific user.
     * @param user
     * @return
     */
    public List<Message> getUsersMessages(User user) {
        return messageManager.getUsersMessages(user);
    }

    /**
     * Retrieves all messages from the database.
     * @return
     */
    public ObservableList<Message> getAllMessages(){
        return messageManager.getAllMessages();
    }

    /**
     * Get all messages related to a specific user, retrieved by UserName.
     * @param userName
     * @return
     */
    public List<Message> getAllUserMessages(String userName) {
        return messageManager.getUsersMessages(userName);
    }

    /**
     * Loads a ScreenBit's messages to it, from the database.
     * @param screen
     */
    public void loadScreenBitsMessages(ScreenBit screen) {
        messageManager.loadScreenBitsMessages(screen);
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
