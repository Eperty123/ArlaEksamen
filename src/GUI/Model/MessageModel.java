package GUI.Model;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MessageModel {

    private static MessageModel instance;
    private MessageManager messageManager;


    private MessageModel(){
        messageManager = new MessageManager();


    }

    public static MessageModel getInstance() {
        return instance == null ? instance = new MessageModel() : instance;
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits){
        messageManager.addMessage(user, newMessage, assignedScreenBits);
    }

    public List<Message> getUsersMessages(User user) {
        return messageManager.getUsersMessages(user);
    }
}
