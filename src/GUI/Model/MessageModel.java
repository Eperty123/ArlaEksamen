package GUI.Model;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;
import DAL.MessageDAL;
import DAL.ScreenDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;

public class MessageModel {

    private static MessageModel instance;
    private MessageManager messageManager;
    private ObservableList<Message> allMessages;

    private MessageModel(){
        messageManager = new MessageManager();
        allMessages = FXCollections.observableArrayList();
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
        allMessages.add(newMessage);
    }

    public void deleteMessage(Message message){
        messageManager.deleteMessage(message);
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        messageManager.updateMessage(oldMessage, newMessage);
        allMessages.remove(oldMessage);
        allMessages.add(newMessage);
    }

    public void loadUserMessages(User user) {
        if (user != null) allMessages.setAll(getUsersMessages(user));
    }

    public List<Message> getUsersMessages(User user) {
        return messageManager.getUsersMessages(user);
    }

    public ObservableList<Message> getAllUserMessages() {
        return allMessages;
    }

    /**
     * Reset the singleton instance.
     */
    public void resetSingleton() {
        if (instance != null) {
            instance = null;
            System.out.println(String.format("%s singleton was reset.", getClass().getSimpleName()));
        }
    }

    public void deleteMessage(Message message){
        messageManager.deleteMessage(message);
    }
}
