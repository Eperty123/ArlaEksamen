package BE;

public class Email {
    private String recipient;
    private String sender;
    private String subject;
    private String message;

    public Email() {

    }

    public Email(String sender) {
        setSender(sender);
    }

    public Email(String recipient, String sender, String subject, String message) {
        setRecipient(recipient);
        setSender(sender);
        setSubject(subject);
        setMessage(message);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
