package com.example.tfg.javi;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailSender {

    private static final String SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String SMTP_HOST_KEY = "mail.smtp.host";
    private static final String SMTP_PORT_KEY = "mail.smtp.port";

    private final String username;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;

    public GmailSender(String username, String password, String smtpHost, String smtpPort) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put(SMTP_AUTH_KEY, "true");
        props.put(SMTP_STARTTLS_KEY, "true");
        props.put(SMTP_HOST_KEY, smtpHost);
        props.put(SMTP_PORT_KEY, smtpPort);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);
        }
    }
}