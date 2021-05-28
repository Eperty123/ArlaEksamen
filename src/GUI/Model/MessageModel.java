package GUI.Model;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {
        messageManager.addMessage(user, newMessage, assignedScreenBits);
    }

    public void deleteMessage(Message message){
        messageManager.deleteMessage(message);
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        messageManager.updateMessage(oldMessage, newMessage);
    }


    public List<Message> getUsersMessages(User user) {
        return messageManager.getUsersMessages(user);
    }


    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
        }
    }

    public ObservableList<Message> getAllMessages(){
        return messageManager.getAllMessages();
    }

    public List<Message> getAllUserMessages(String userName) {
        return messageManager.getUsersMessages(userName);
    }

    public void loadScreenBitsMessages(ScreenBit screen) {
        messageManager.loadScreenBitsMessages(screen);
    }
}
