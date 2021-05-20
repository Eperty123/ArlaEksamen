package BLL;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.MessageDAL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MessageManager {

    private MessageDAL messageDAL;

    public MessageManager() {
        messageDAL = new MessageDAL();
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {
        messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    public void deleteMessage(Message message) {
        messageDAL.deleteMessage(message);
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        messageDAL.updateMessage(oldMessage, newMessage);
    }

    public List<Message> getUsersMessages(User user) {
        return messageDAL.getUsersMessages(user);
    }

    public List<Message> getUsersMessages(String user) {
        return messageDAL.getUsersMessages(user);
    }


    public void loadScreenBitsMessages(ScreenBit screen) {
        messageDAL.loadScreenBitsMessages(screen);
    }

    public ObservableList<Message> getAllMessages() {
        return FXCollections.observableArrayList(messageDAL.getAllMessages());
    }


}