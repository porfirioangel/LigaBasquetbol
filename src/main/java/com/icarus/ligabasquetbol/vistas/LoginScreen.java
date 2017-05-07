package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.icarus.ligabasquetbol.utils.Sha1;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@StyleSheet({"vaadin://lib/css/bootstrap.min.css"})
@StyleSheet({"vaadin://lib/css/login_screen.css"})
@JavaScript({"vaadin://lib/js/jquery.min.js"})
@JavaScript({"vaadin://lib/js/bootstrap.min.js"})
@JavaScript({"vaadin://lib/js/automaticmodal.js"})
public class LoginScreen extends CustomLayout {
    private TextField tfEmail;
    private PasswordField pfPassword;
    private Button btnLogin;
    private Button btnRecuperar;
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
        btnRecuperar = new Button("Recuperar contraseña");
        btnRecuperar.addStyleName(ValoTheme.BUTTON_LINK);
        addComponent(tfEmail, "tfEmail");
        addComponent(pfPassword, "pfPassword");
        addComponent(btnLogin, "btnLogin");
        addComponent(btnRecuperar, "btnRecuperar");
        tfEmail.setValue("porfirioads@gmail.com");
        pfPassword.setValue("holamundo");
    }

    private void addBtnLoginClickListener() {
        btnLogin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario = accesoUsuario.loguear(tfEmail.getValue(),
                        Sha1.toSha1(pfPassword.getValue()));
                if (usuario == null)
                    Notification.show("Datos incorrectos",
                            Notification.Type.ERROR_MESSAGE);
                else
                    Notification.show("Datos correctos",
                            Notification.Type.TRAY_NOTIFICATION);
            }
        });
    }
}
