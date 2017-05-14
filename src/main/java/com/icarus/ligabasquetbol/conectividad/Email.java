package com.icarus.ligabasquetbol.conectividad;


import com.icarus.ligabasquetbol.fileio.FileRW;
import com.vaadin.ui.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "porfirioads@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "porfirioads@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            message.setContent(message, "text/html; charset=utf-8");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>This is actual message</h1>", "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void enviarEmail(String emailAddress) {
        try {
            Properties propiedades = System.getProperties();
            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "ligabasquetboljerez@gmail.com", "liga.basquetbol");
                }
            };

            Session sesion = Session.getDefaultInstance(propiedades,
                    authenticator);

            MimeMessage mensaje = new MimeMessage(sesion);

            mensaje.addRecipient(Message.RecipientType.TO, new
                    InternetAddress(emailAddress));

            mensaje.setSubject("Confirmación de cuenta - Liga Basquetbol");

            String filename = "layouts/email_confirmacion.html";
            String html = FileRW.leerArchivoDelTema(filename);

            System.out.println("email>>> " + html);

            mensaje.setContent(html, "text/html; charset=utf-8");

            Transport.send(mensaje);

        } catch (MessagingException e) {
            Notification.show("No se puede enviar el correo de confirmación, "
                            + "error: " + e.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }
}
