package com.icarus.ligabasquetbol.conectividad;


import com.icarus.ligabasquetbol.fileio.FileRW;
import com.vaadin.ui.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    public void enviarEmailConfirmacion(String emailAddress, String
            nombreUsuario, String codigoConfirmacion) {
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
            String html = FileRW.leerArchivoDelTema(filename)
                    .replace("{{ nombreUsuario }}", nombreUsuario)
                    .replace("{{ codigoConfirmacion }}", codigoConfirmacion);

            mensaje.setContent(html, "text/html; charset=utf-8");

            Transport.send(mensaje);

        } catch (MessagingException e) {
            Notification.show("No se puede enviar el correo de confirmación, "
                            + "error: " + e.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }
}
