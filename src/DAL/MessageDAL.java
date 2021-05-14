package DAL;

import BE.Message;
import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import com.mysql.cj.Messages;

import java.sql.*;
import java.time.LocalDateTime;
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
    public List<Message> getUsersMessages(User user) {
        List<Message> messages = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messages;
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Message VALUES(?,?,?,?,?,?)");
            pSql.setInt(1, newMessage.getId());
            pSql.setString(2, newMessage.getMessage());
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(4, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(5, String.valueOf(newMessage.getTextColor()));
            pSql.setInt(6, newMessage.getMessageType().ordinal());

            pSql.execute();

            bookTimeSlots(con, newMessage, assignedScreenBits);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            assignScreenBitMessages(user, newMessage, assignedScreenBits);
    }

    private void bookTimeSlots(Connection con, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {

        List<LocalDateTime> timeSlots = getSlots(newMessage);
        System.out.println("TimeSlots: " + timeSlots.size());

        PreparedStatement pSql = con.prepareStatement("UPDATE ScreenTime SET Available=? WHERE ScreenId=? AND TimeSlot=?");
        for(ScreenBit s : assignedScreenBits){
            for(int i = 0; i < timeSlots.size(); i++){
                pSql.setBoolean(1,false);
                pSql.setInt(2, s.getId());
                pSql.setTimestamp(3, Timestamp.valueOf(timeSlots.get(i)));
                pSql.addBatch();
            }
        }
        pSql.executeBatch();
    }


    private List<LocalDateTime> getSlots(Message newMessage) {
        LocalDateTime start = newMessage.getMessageStartTime();
        LocalDateTime end = newMessage.getMessageEndTime();
        List<LocalDateTime> timeSlots = new ArrayList<>();


        timeSlots.add(start);

        // Get amount of 30 minute time slots to book
        int slotCount = ((end.getHour()- start.getHour()) * 2) + ((end.getMinute()-start.getMinute() == 0 ? 0 : 1));
        System.out.println("slotCount =" + slotCount);
        for(int i = 1; i < slotCount; i++){
            timeSlots.add(start.plusMinutes(i * 30));
        }

        return timeSlots;
    }

    // TODO NOT DONE
    private void assignScreenBitMessages(User user, Message message, List<ScreenBit> assignedScreenBits) {
        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenMessage VALUES (?,?)");

            for(ScreenBit s : assignedScreenBits){
                pSql.setInt(1,message.getId());
                pSql.setInt(2, s.getId());
                pSql.addBatch();
            }
            pSql.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
