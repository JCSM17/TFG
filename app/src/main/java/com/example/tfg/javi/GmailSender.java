package com.example.tfg.javi;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailSender {

    // Constantes para las claves de configuración SMTP.
    private static final String SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String SMTP_HOST_KEY = "mail.smtp.host";
    private static final String SMTP_PORT_KEY = "mail.smtp.port";

    private final String username;  // Nombre de usuario para la autenticación SMTP
    private final String password;  // Contraseña del usuario para la autenticación SMTP
    private final String smtpHost;  // Host SMTP para el servidor de correo saliente
    private final String smtpPort;  // Puerto SMTP para el servidor de correo saliente

    public GmailSender(String username, String password, String smtpHost, String smtpPort) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    // Método para enviar un correo electrónico
    public void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put(SMTP_AUTH_KEY, "true");  // Habilita la autenticación SMTP
        props.put(SMTP_STARTTLS_KEY, "true");  // Habilita STARTTLS para la conexión segura
        props.put(SMTP_HOST_KEY, smtpHost);  // Configura el host SMTP
        props.put(SMTP_PORT_KEY, smtpPort);  // Configura el puerto SMTP

        // Crea una sesión de correo electrónico con autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crea un mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));  // Establece el remitente del correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));  // Establece el destinatario del correo
            message.setSubject(subject);  // Establece el asunto del correo
            message.setText(body);  // Establece el cuerpo del correo

            Transport.send(message);  // Envía el mensaje de correo
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);  // Captura y lanza excepciones de mensajería
        }
    }
}