package BLL;

import BE.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailManager {

    private String host;
    private int port;
    private String username;
    private String password;
    private boolean useTLS;

    private static EmailManager instance;

    public EmailManager() {
        this.initialize();
    }

    private void initialize() {
        this.loadEmailSettings("src/Resources/email.settings");
    }

    public void loadEmailSettings(String settingsFile) {
        var file = new File(settingsFile);
        if (file.exists()) {
            Properties emailSettings = new Properties();

            try {
                emailSettings.load(new FileInputStream(file));
                this.setHost(emailSettings.getProperty("Host"));
                this.setPort(Integer.parseInt(emailSettings.getProperty("Port")));
                this.setUseTLS(Boolean.parseBoolean(emailSettings.getProperty("UseTLS")));
                this.setUsername(emailSettings.getProperty("Username"));
                this.setPassword(emailSettings.getProperty("Password"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUseTLS() {
        return this.useTLS;
    }

    public void setUseTLS(boolean useTLS) {
        this.useTLS = useTLS;
    }

    /**
     * Send an email to the given recipient with a given sender, subject and message.
     *
     * @param recipient The recipient of the email.
     * @param sender    The sender of the email.
     * @param subject   The subject of the email.
     * @param message   The message of the email.
     * @return Returns true if send is successful otherwise false.
     */
    public boolean sendTo(String recipient, String sender, String subject, String message) {
        return this.sendTo(new Email(recipient, sender, subject, message));
    }

    /**
     * Send an email to the specified Email instance with all information given.
     *
     * @param email The Email instance to read and send email from/to.
     * @return Returns true if send is successful otherwise false.
     */
    public boolean sendTo(Email email) {

        try {

            // We create a new Properties object here for the email sender vendor itself.
            // We will use some of the email settings properties though.
            Properties prop = new Properties();
            prop.put("mail.smtp.host", this.getHost());
            prop.put("mail.smtp.port", this.getPort());
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", this.isUseTLS()); //TLS

            // Get the Session object.
            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EmailManager.this.username, EmailManager.this.password);
                        }
                    });

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(email.getSender()));

            // Set To: header field of the header.

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email.getRecipient()));

            // Set Subject: header field
            message.setSubject(email.getSubject());

            // Now set the actual message
            message.setText(email.getMessage());

            // Send message
            Transport.send(message);

            System.out.println(String.format("Email sent successfully to: %s.", email.getRecipient()));
            return true;

        } catch (MessagingException e) {
            System.out.println(String.format("Failed to send email to: %s. Please check email settings!", email.getRecipient()));
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the singleton instance of EmailManager.
     * @return Returns the instance of said class.
     */
    public static EmailManager getInstance() {
        return EmailManager.instance == null ? EmailManager.instance = new EmailManager() : EmailManager.instance;
    }

    public boolean canSendEmail() {
        return EmailExtension.isEmailValid(username);
    }
}
