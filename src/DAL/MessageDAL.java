package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;
import javafx.scene.paint.Color;

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
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while(rs.next()){
                int id = rs.getInt("id");
                String message = rs.getString("Message");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
                Color textColor = Color.valueOf((rs.getString("TextColor")));
                MessageType messageType = rs.getBoolean("MessageType") ? MessageType.Admin : MessageType.Manager;

                Message newMessage = new Message(id, message, startTime, endTime, textColor, messageType);
                messages.add(newMessage);
            }

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all messages " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

        return messages;
    }

    // TODO
    public List<Message> getUsersMessages(User user) {
        List<Message> messages = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message WHERE Author=?");
            pSql.setString(1, user.getUserName());
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while(rs.next()){
                int id = rs.getInt("id");
                String message = rs.getString("Message");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
                Color textColor = Color.valueOf((rs.getString("TextColor")));
                MessageType messageType = rs.getBoolean("MessageType") ? MessageType.Admin : MessageType.Manager;

                Message newMessage = new Message(id, message, startTime, endTime, textColor, messageType);
                messages.add(newMessage);
            }

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all messages for the specified user" +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

        return messages;
    }

    public void getScreenBitsMessages(ScreenBit screenBit)  {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT [Message].*, " +
                            "ScreenMessage.ScreenId AS ScreenId " +
                            "FROM Message " +
                            "LEFT OUTER JOIN ScreenMessage " +
                            "ON [Message].Id = ScreenMessage.MessageId");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String message = rs.getString("Message");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
                Color textColor = Color.valueOf((rs.getString("TextColor")));
                MessageType messageType = rs.getBoolean("MessageType") ? MessageType.Admin : MessageType.Manager;

                Message newMessage = new Message(id, message, startTime, endTime, textColor, messageType);
                screenBit.addMessage(newMessage);
            }
        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to get all messages for the specified screen" +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) {

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Message VALUES(?,?,?,?,?,?,?)");
            pSql.setInt(1, newMessage.getId());
            pSql.setString(2, newMessage.getMessage());
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(4, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(5, String.valueOf(newMessage.getTextColor()));
            pSql.setInt(6, newMessage.getMessageType().ordinal());
            pSql.setString(7, user.getUserName());

            pSql.execute();

            bookTimeSlots(con, newMessage, assignedScreenBits);

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a message " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
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

        //TODO explain better
        // Get amount of 30 minute time slots to book
        int endb = (end.getHour() * 2) + (end.getMinute()== 0 ? 0 : 1);
        int startb = (start.getHour() * 2) + (start.getMinute()== 0 ? 0 : 1);
        if(end.getDayOfMonth() > start.getDayOfMonth()){
            endb += 48 * (end.getDayOfMonth() - start.getDayOfMonth());
        }
        int slotCount = endb - startb;

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
            WarningController.createWarning("Oh no! Something went wrong when attempting to make an associtaion between " +
                    "a list of screens and a message in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    // TODO
    public void deleteMessage(Message message) {

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Message WHERE Id=?");
            pSql.setInt(1, message.getId());
            pSql.execute();
            
        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a message " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }


    public void updateMessage(Message oldMessage, Message newMessage) {

        try(Connection con = dbCon.getConnection()){

            PreparedStatement pSql = con.prepareStatement(
                    "UPDATE Message " +
                        "SET Message=?," +
                        "StartTime=?, " +
                            "EndTime=?, " +
                            "TextColor=? " +
                        "WHERE Id=?");

            pSql.setString(1, newMessage.getMessage());
            pSql.setTimestamp(2, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(4, String.valueOf(newMessage.getTextColor()));
            pSql.setInt(5, oldMessage.getId());

            pSql.executeUpdate();

        } catch (SQLException throwables) {
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a message " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }

    }



}
