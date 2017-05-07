package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@StyleSheet({"vaadin://lib/css/bootstrap.min.css"})
@StyleSheet({"vaadin://lib/css/login_screen.css"})
@JavaScript({"vaadin://lib/js/jquery.min.js"})
@JavaScript({"vaadin://lib/js/bootstrap.min.js"})
@JavaScript({"vaadin://lib/js/automaticmodal.js"})
public class CrearCuentaScreen extends CustomLayout {
    private TextField tfNombre;
    private TextField tfApPaterno;
    private TextField tfApMaterno;
    private TextField tfTelefono;
    private DateField dfFechaNacimiento;
    private UploadFile upFoto;
    private TextField tfEmail;
    private PasswordField pfPassword;
    private Button btnCrearCuenta;
    private Image imgFoto;
    private AccesoUsuario accesoUsuario;
    private AccesoEntrenador accesoEntrenador;

    public CrearCuentaScreen() {
        setTemplateName("crear_cuenta_screen");
        accesoUsuario = new AccesoUsuario();
        crearElementos();
    }

    private void crearElementos() {
        tfNombre = new TextField("Nombre");
        tfNombre.addStyleName("full-width");
        tfApPaterno = new TextField("Apellido paterno");
        tfApPaterno.addStyleName("full-width");
        tfApMaterno = new TextField("Apellido materno");
        tfApMaterno.addStyleName("full-width");
        tfTelefono = new TextField("Teléfono");
        tfTelefono.addStyleName("full-width");
        dfFechaNacimiento = new DateField("Fecha de nacimiento");
        dfFechaNacimiento.addStyleName("full-width");
        upFoto = new UploadFile("Foto de perfil");
        upFoto.addStyleName("full-width");
        upFoto.setButtonCaption("Seleccionar foto");
        tfEmail = new TextField("Email");
        tfEmail.addStyleName("full-width");
        pfPassword = new PasswordField("Contraseña");
        pfPassword.addStyleName("full-width");
        btnCrearCuenta = new Button("Crear cuenta");
        btnCrearCuenta.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnCrearCuenta.addStyleName("full-width");
        imgFoto = new Image();
        imgFoto.setVisible(false);
        imgFoto.addStyleName("full-width");
        addBtnCrearCuentaClickListener();
        addUpFotoFinishedListener();
        addComponent(tfNombre, "tfNombre");
        addComponent(tfApPaterno, "tfApPaterno");
        addComponent(tfApMaterno, "tfApMaterno");
        addComponent(tfTelefono, "tfTelefono");
        addComponent(dfFechaNacimiento, "dfFechaNacimiento");
        addComponent(upFoto, "foto");
        addComponent(tfEmail, "tfEmail");
        addComponent(pfPassword, "pfPassword");
        addComponent(btnCrearCuenta, "btnCrearCuenta");
        addComponent(imgFoto, "imgFoto");
    }

    private void addUpFotoFinishedListener() {
        upFoto.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent event) {
                imgFoto.setVisible(true);
                imgFoto.setSource(new FileResource(upFoto.getFile()));
            }
        });
    }

    private void addBtnCrearCuentaClickListener() {

    }
}
