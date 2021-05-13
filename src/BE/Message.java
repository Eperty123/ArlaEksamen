package BE;

import javafx.scene.paint.Color;

import java.time.LocalDateTime;



public class Message {
    private int id;
    private LocalDateTime messageStartTime;
    private LocalDateTime messageEndTime;
    private String message;
    private Color textColor;
    private MessageType messageType;

    private final int SHIFT_DURATION = 8;

    public Message(LocalDateTime messageStartTime, LocalDateTime messageEndTime, String message, Color textColor, MessageType messageType) {
        this.messageStartTime = messageStartTime;
        this.messageEndTime = messageEndTime;
        this.message = message;
        this.textColor = textColor;
        this.messageType = messageType;
        setId(message, messageStartTime, messageEndTime);
    }

    public Message(LocalDateTime messageStartTime, LocalDateTime messageEndTime, String message, Color textColor) {
        this.messageStartTime = messageStartTime;
        this.messageEndTime = messageEndTime;
        this.message = message;
        this.textColor = textColor;
        setId(message, messageStartTime, messageEndTime);
    }

    public Message(LocalDateTime messageStartTime, String message, Color textColor) {
        this.messageStartTime = messageStartTime;
        this.message = message;
        this.textColor = textColor;
        this.messageEndTime = messageStartTime.plusHours(SHIFT_DURATION);
        setId(message, messageStartTime, this.messageEndTime);
    }



    public Message(String message) {
        this.message = message;
        this.textColor = Color.RED; //Default
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getMessageStartTime() {
        return messageStartTime;
    }

    public void setMessageStartTime(LocalDateTime messageStartTime) {
        this.messageStartTime = messageStartTime;
    }

    public LocalDateTime getMessageEndTime() {
        return messageEndTime;
    }

    public void setMessageEndTime(LocalDateTime messageEndTime) {
        this.messageEndTime = messageEndTime;
    }

    public int getSHIFT_DURATION() {
        return SHIFT_DURATION;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setId(String message, LocalDateTime messageStartTime, LocalDateTime messageEndTime){
        int messageHash = message.hashCode();
        int startTimeHash = messageStartTime.hashCode();
        int endTimeHash = messageEndTime.hashCode();

        this.id = messageHash + startTimeHash + endTimeHash;
    }


}
