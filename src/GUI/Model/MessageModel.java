package GUI.Model;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import BLL.MessageManager;

import java.util.List;

public class MessageModel {

    private static MessageModel instance;
    private MessageManager messageManger;

    private MessageModel(){
        messageManger = new MessageManager();

    }

    public static MessageModel getInstance() {
        return instance == null ? instance = new MessageModel() : instance;
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits){
        messageManger.addMessage(user, newMessage, assignedScreenBits);
    }

}
