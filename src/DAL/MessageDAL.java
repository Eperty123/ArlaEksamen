package DAL;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import com.mysql.cj.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageDAL {

    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

    // TODO
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messages;
    }

    // TODO
    public List<Message> getUserMessages(User user) {
        List<Message> messages = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messages;
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {

        if(!assignedScreenBits.isEmpty()){
            assignScreenBitMessages(assignedScreenBits);
        }

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Message VALUES(?,?,?,?)");
            pSql.setString(1, newMessage.getMessage());
            pSql.setTimestamp(2, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(4, String.valueOf(newMessage.getTextColor()));
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // TODO
    private void assignScreenBitMessages(List<ScreenBit> assignedScreenBits) {
    }

    // TODO
    public void deleteMessage(Message newMessage) {

        try(Connection con = dbCon.getConnection()){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // TODO
    public void updateMessage(Message oldMessage, Message newMessage) {

        try(Connection con = dbCon.getConnection()){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}
