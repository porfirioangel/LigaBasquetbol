package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.icarus.ligabasquetbol.utils.Sha1;
import com.icarus.ligabasquetbol.vistas.designs.AdminDashboardScreen;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@StyleSheet({"vaadin://lib/css/bootstrap.min.css"})
@StyleSheet({"vaadin://lib/css/login_screen.css"})
@JavaScript({"vaadin://lib/js/jquery.min.js"})
@JavaScript({"vaadin://lib/js/bootstrap.min.js"})
@JavaScript({"vaadin://lib/js/automaticmodal.js"})
public class LoginScreen extends CustomLayout {
    private TextField tfEmail;
    private PasswordField pfPassword;
    private Button btnLogin;
    private AccesoUsuario accesoUsuario;

    public LoginScreen() {
        setTemplateName("login_screen");
        accesoUsuario = new AccesoUsuario();
        crearElementos();
    }

    private void crearElementos() {
        tfEmail = new TextField("Email");
        tfEmail.addStyleName("full-width");
        pfPassword = new PasswordField("Contraseña");
        pfPassword.addStyleName("full-width");
        btnLogin = new Button("Ingresar");
        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnLogin.addStyleName("full-width");
        addBtnLoginClickListener();
        addComponent(tfEmail, "tfEmail");
        addComponent(pfPassword, "pfPassword");
        addComponent(btnLogin, "btnLogin");
        tfEmail.setValue("porfirioads@gmail.com");
        pfPassword.setValue("holamundo");
    }

    private void addBtnLoginClickListener() {
        btnLogin.addClickListener((Button.ClickListener) event -> {
            Usuario usuario = accesoUsuario.loguear(tfEmail.getValue(),
                    Sha1.toSha1(pfPassword.getValue()));
            if (usuario == null)
                Notification.show("Datos incorrectos",
                        Notification.Type.ERROR_MESSAGE);
            else {
                if (usuario.getTipoUsuario().equals("entrenador")) {
                    Notification.show("Bienvenido entrenador",
                            Notification.Type.TRAY_NOTIFICATION);
                    // TODO Ver qué se hará en esta parte :s
                } else if (usuario.getTipoUsuario().equals("administrador")) {
                    Page.getCurrent().getJavaScript().execute(
                            "$('.modal-backdrop').remove();");
                    UI.getCurrent().setContent(new AdminDashboardScreen());
                    Notification.show("Bienvenido administrador",
                            Notification.Type.TRAY_NOTIFICATION);
                } else {
                    Notification.show("Bienvenido usuario normal",
                            Notification.Type.TRAY_NOTIFICATION);
                }
            }
        });
    }
}
