package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IMessageCRUD {
    void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException;

    void deleteMessage(Message message) throws SQLException;

    void updateMessage(Message oldMessage, Message newMessage) throws SQLException;

    List<Message> getUsersMessages(User user);

    List<Message> getUsersMessages(String user);

    void loadScreenBitsMessages(ScreenBit screen) throws SQLException;

    ObservableList<Message> getAllMessages();

    boolean hasMessagesLoaded();
}
