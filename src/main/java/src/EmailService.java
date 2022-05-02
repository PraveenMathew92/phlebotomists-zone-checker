package src;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private final String username = "pm.777rocky@gmail.com";
    private final String password = System.getenv("GMAIL_KEY");
    private final Properties prop;
    private final Session session;
    private final InternetAddress senderEmailAddress;
    private final InternetAddress receiverAddress;

    public EmailService() throws AddressException {
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        senderEmailAddress = new InternetAddress("pm.777rocky@gmail.com");
        receiverAddress = new InternetAddress("engineering+challenge@sprinterhealth.com");
    }

    public void sendMail(int phlebotomistId) {
        System.out.println("Sending Alert for phlebotomist" + phlebotomistId);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(senderEmailAddress);
            message.setRecipient(Message.RecipientType.TO, receiverAddress);
            message.setSubject("ALERT - MISSING PHLEBOTOMIST ID " + phlebotomistId);
            message.setText("phlebotomist " + phlebotomistId + " is missing from the range");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
