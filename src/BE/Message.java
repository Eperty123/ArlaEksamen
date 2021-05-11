package BE;

import javafx.scene.paint.Color;

import java.time.LocalDateTime;



public class Message {
    private LocalDateTime messageStartTime;
    private LocalDateTime messageEndTime;
    private String message;
    private Color color;

    private final int SHIFT_DURATION = 8;

    public Message(LocalDateTime messageStartTime, LocalDateTime messageEndTime, String message, Color color) {
        this.messageStartTime = messageStartTime;
        this.messageEndTime = messageEndTime;
        this.message = message;
        this.color = color;
    }

    public Message(LocalDateTime messageStartTime, String message, Color color) {
        this.messageStartTime = messageStartTime;
        this.message = message;
        this.color = color;
        messageEndTime = messageStartTime.plusHours(SHIFT_DURATION);
    }

    public Message(String message, Color color) {
        this.message = message;
        this.color = color;
    }

    public Message(String message) {
        this.message = message;
        this.color = Color.RED; //Default
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
