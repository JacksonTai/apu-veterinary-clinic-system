package service.emailNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static config.ApplicationProperty.*;

public class EmailNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    public static void sendMail(String receiverEmail, String subject, String message) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.user", SENDER_EMAIL);
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", SMTP_PORT);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", SMTP_PORT);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");

        try {
            Authenticator auth = new SMTPAuthenticator(SENDER_EMAIL, SENDER_PASSWORD);
            Session session = Session.getInstance(properties, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject("AVCS - " + subject);
            msg.setText(message);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            Transport.send(msg);
        } catch (MessagingException e) {
            logger.error("Error sending email", e);
        }
    }

}
