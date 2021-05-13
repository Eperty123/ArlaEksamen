package BE;

import javafx.scene.paint.Color;

import java.time.LocalDateTime;



public class Message {
    private int id;
    private LocalDateTime messageStartTime;
    private LocalDateTime messageEndTime;
    private String message;
    private Color textColor;

    private final int SHIFT_DURATION = 8;

    public Message(LocalDateTime messageStartTime, LocalDateTime messageEndTime, String message, Color textColor) {
        this.messageStartTime = messageStartTime;
        this.messageEndTime = messageEndTime;
        this.message = message;
        this.textColor = textColor;
    }

    public Message(LocalDateTime messageStartTime, String message, Color textColor) {
        this.messageStartTime = messageStartTime;
        this.message = message;
        this.textColor = textColor;
        messageEndTime = messageStartTime.plusHours(SHIFT_DURATION);
    }

    public Message(String message, Color textColor) {
        this.message = message;
        this.textColor = textColor;
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
}
