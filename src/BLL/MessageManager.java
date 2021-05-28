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

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {
        this.messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    public void deleteMessage(Message message) {
        this.messageDAL.deleteMessage(message);
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        this.messageDAL.updateMessage(oldMessage, newMessage);
    }

    public List<Message> getUsersMessages(User user) {
        return this.messageDAL.getUsersMessages(user);
    }

    public List<Message> getUsersMessages(String user) {
        return this.messageDAL.getUsersMessages(user);
    }


    public void loadScreenBitsMessages(ScreenBit screen) {
        this.messageDAL.loadScreenBitsMessages(screen);
    }

    public ObservableList<Message> getAllMessages() {
        return FXCollections.observableArrayList(this.messageDAL.getAllMessages());
    }


}