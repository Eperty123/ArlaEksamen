package BLL;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.MessageDAL;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private MessageDAL messageDAL;

    public MessageManager() {
        messageDAL = new MessageDAL();
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {
        messageDAL.addMessage(user, newMessage, assignedScreenBits);
    }

    public void deleteMessage(Message message){
        messageDAL.deleteMessage(message);
    }

    public void updateMessage(Message oldMessage, Message newMessage) {
        messageDAL.updateMessage(oldMessage, newMessage);
    }

    public List<Message> getUsersMessages(User user) {
        List<Message> tmp = new ArrayList<>();

        if (user != null) {
            user.getAssignedScreen().forEach(screenBit -> {
                messageDAL.loadScreenBitsMessages(screenBit);
            });
            user.getAssignedScreen().forEach(screenBit -> screenBit.getMessages().forEach((msg) -> {
                if (!tmp.contains(msg))
                    tmp.add(msg);
            }));
        }
        return tmp;
    }

    public void loadScreenBitsMessages(ScreenBit screen) {
        messageDAL.loadScreenBitsMessages(screen);
    }

}
