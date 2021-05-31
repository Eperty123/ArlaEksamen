package DAL;

import BE.Message;
import BE.MessageType;
import BE.ScreenBit;
import BE.User;
import BLL.TimeSlotCalculator;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;
import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();

    /**
     * Retrieves all messages from the database.
     *
     * @return a list of all messages.
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            while (rs.next()) {
                messages.add(getMessage(rs));
            }

            return messages;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //throw throwables;
            return null;
        }
    }

    /**
     * Retrieves a list of messages, specific to a user.
     *
     * @param user user who's messages are retrieved.
     * @return List
     */
    public List<Message> getUsersMessages(User user) {
        List<Message> messages = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message WHERE Author=?");
            pSql.setString(1, user.getUserName());
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                messages.add(getMessage(rs));
            }

            return messages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //throw throwables;
            return null;
        }
    }

    public List<Message> getUsersMessages(String user) {
        List<Message> messages = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Message WHERE Author=?");
            pSql.setString(1, user);
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                messages.add(getMessage(rs));
            }
            return messages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //throw throwables;
            return null;
        }
    }

    /**
     * Creates a Message object from a ResultSet row.
     *
     * @param rs
     * @throws SQLException
     */
    private Message getMessage(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String message = rs.getString("Message");
        LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
        LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
        Color textColor = Color.valueOf((rs.getString("TextColor")));
        MessageType messageType = rs.getBoolean("MessageType") ? MessageType.Admin : MessageType.Manager;

        Message newMessage = new Message(id, message, startTime, endTime, textColor, messageType);
        return newMessage;
    }

    /**
     * Retrieves all messages assigned to a ScreenBit, adds the messages to the ScreenBit instance.
     *
     * @param screenBit ScreenBit to
     */
    public void loadScreenBitsMessages(ScreenBit screenBit) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT [Message].*, " +
                            "ScreenMessage.ScreenId AS ScreenId " +
                        "FROM Message " +
                        "LEFT OUTER JOIN ScreenMessage " +
                        "    ON [Message].Id = ScreenMessage.MessageId " +
                        "    WHERE ScreenId=?");
            pSql.setInt(1, screenBit.getId());
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                screenBit.addMessage(getMessage(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Adds the new message to the database, books the relevant time slots in the ScreenBit(s) time table,
     * and creates associations between the message and the ScreenBit(s) it is assigned to.
     *
     * @param user               used to set the message's author in the database.
     * @param newMessage         Object containing the new message information.
     * @param assignedScreenBits ScreenBit(s) to have the message assigned to it.
     */
    public void addMessage(User user, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            PreparedStatement pSql = con.prepareStatement("" +
                    "INSERT INTO Message VALUES(?,?,?,?,?,?,?)");
            pSql.setInt(1, newMessage.getId());
            pSql.setString(2, newMessage.getMessage());
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(4, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(5, String.valueOf(newMessage.getTextColor()));
            pSql.setInt(6, newMessage.getMessageType().ordinal());
            pSql.setString(7, user.getUserName());

            try {
                pSql.execute();
                // Toggle's the ScreenBits timetable, so that it is booked during the message's duration.
                if (newMessage.getMessageType() != MessageType.Admin) {
                    bookTimeSlots(con, newMessage, assignedScreenBits);
                }

            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
        // Creates associations between message and ScreenBit(s) in the ScreenMessage junction table.
        assignScreenBitMessages(user, newMessage, assignedScreenBits);
    }

    /**
     * A ScreenBit has a time table with possible time slots for a message to be displayed. When assigning a
     * message, the time slots corresponding to the duration of the message will be set du "unavailable".
     *
     * @param con                Connection to the database.
     * @param newMessage         Object
     * @param assignedScreenBits
     * @throws SQLException
     */
    private void bookTimeSlots(Connection con, Message newMessage, List<ScreenBit> assignedScreenBits) throws SQLException {

        List<LocalDateTime> timeSlots = getSlots(newMessage);

        PreparedStatement pSql = con.prepareStatement(
                "UPDATE ScreenTime " +
                        "SET Available=? " +
                        "    WHERE ScreenId=? " +
                        "     AND TimeSlot=?");
        for (ScreenBit s : assignedScreenBits) {
            for (int i = 0; i < timeSlots.size(); i++) {
                pSql.setBoolean(1, false);
                pSql.setInt(2, s.getId());
                pSql.setTimestamp(3, Timestamp.valueOf(timeSlots.get(i)));
                pSql.addBatch();
            }
        }
        pSql.executeBatch();
    }

    /**
     * Creates a list of LocalDateTime objects based on a message's start- and end-display time.
     * This list is used to book specific slots in a ScreenBit's time table.
     *
     * @param newMessage object containing the LocalDateTime information.
     * @return list of time slots to book.
     */
    private List<LocalDateTime> getSlots(Message newMessage) {
        List<LocalDateTime> timeSlots = new ArrayList<>();
        int slotCount = TimeSlotCalculator.calculateTimeSlots(newMessage);

        // Adding LocalDateTime objects with 30 minute increments
        // (appropriate for the ScreenBit's time table.)
        for (int i = 0; i < slotCount; i++) {
            timeSlots.add(newMessage.getMessageStartTime().plusMinutes(i * 30));
        }
        return timeSlots;
    }

    /**
     * Creates an association between one or more ScreenBits, and a Message
     * in the junction table ScreenMessage in the database. The method uses batches in case more than one ScreenBit
     * has been assigned.
     *
     * @param user               // TODO delete this param?
     * @param message            Object containing message information.
     * @param assignedScreenBits List of ScreenBits to have the message assigned to them.
     */
    private void assignScreenBitMessages(User user, Message message, List<ScreenBit> assignedScreenBits) throws SQLException {
        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO ScreenMessage VALUES (?,?)");

            for (ScreenBit s : assignedScreenBits) {
                pSql.setInt(1, message.getId());
                pSql.setInt(2, s.getId());
                pSql.addBatch();
            }
            pSql.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }


    /**
     * Deletes a message from the database.
     *
     * @param message message to be deleted.
     */
    public void deleteMessage(Message message) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            // Deletes all association entries from the junction table ScreenMessage.
            deleteMessageScreenAssociation(con, message);

            PreparedStatement pSql = con.prepareStatement("DELETE FROM Message WHERE Id=?");
            pSql.setInt(1, message.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

    /**
     * Deletes all association entries from the junction table ScreenMessage.
     *
     * @param con     Connection to the database.
     * @param message object containing the message Id, which is used in the query.
     * @throws SQLException
     */
    private void deleteMessageScreenAssociation(Connection con, Message message) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("DELETE FROM ScreenMessage WHERE MessageId=?");
        pSql.setInt(1, message.getId());
        pSql.execute();
    }

    /**
     * Updates an existing message in the database. Message, start- and end-time, and color can be changed
     * by the client.
     *
     * @param oldMessage object containing the old message info for referencing the database.
     * @param newMessage object containing the new message info for updating the database.
     */
    public void updateMessage(Message oldMessage, Message newMessage) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement(
                    "UPDATE Message " +
                            "SET Message=?," +
                            "StartTime=?, " +
                            "EndTime=?, " +
                            "TextColor=?" +
                            " WHERE Id=?");

            pSql.setString(1, newMessage.getMessage());
            pSql.setTimestamp(2, Timestamp.valueOf(newMessage.getMessageStartTime()));
            pSql.setTimestamp(3, Timestamp.valueOf(newMessage.getMessageEndTime()));
            pSql.setString(4, String.valueOf(newMessage.getTextColor()));
            pSql.setInt(5, oldMessage.getId());

            pSql.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }
}
