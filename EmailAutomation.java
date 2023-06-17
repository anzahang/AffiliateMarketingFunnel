import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAutomation {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USERNAME = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // Email configuration
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String EMAIL_USERNAME = "your_email@gmail.com";
    private static final String EMAIL_PASSWORD = "your_email_password";

    public static void main(String[] args) {
        // Connect to the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Fetch data from the database
            String sql = "SELECT name, email FROM recipients";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Send email to each recipient
                sendEmail(name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void sendEmail(String name, String email) {
        // Email content
        String subject = "Hello, " + name;
        String body = "Dear " + name + ",\n\nThis is a sample automated email.\n\nRegards,\nYour Name";

        // SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session and authenticate with the email server
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent to " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
