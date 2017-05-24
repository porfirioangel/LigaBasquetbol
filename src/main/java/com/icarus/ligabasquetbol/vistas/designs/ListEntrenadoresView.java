package com.icarus.ligabasquetbol.vistas.designs;

import com.icarus.ligabasquetbol.conectividad.Email;
import com.icarus.ligabasquetbol.conectividad.ImageUploader;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.icarus.ligabasquetbol.persistencia.modelos.Entrenador;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.icarus.ligabasquetbol.vistas.components.UploadFile;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.renderers.HtmlRenderer;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;

import java.io.File;
import java.util.List;

public class ListEntrenadoresView extends ListEntrenadoresDesign {
    private AccesoEntrenador accesoEntrenador;
    private Binder<Entrenador> entrenadorBinder;
    private Binder<Usuario> usuarioBinder;
    private UploadFile uploadFoto;
    private Entrenador entrenador;
    private Usuario usuario;
    private boolean modoNuevo;
    private File foto;

    public ListEntrenadoresView() {
        accesoEntrenador = new AccesoEntrenador();
        entrenadorBinder = new Binder<>(Entrenador.class);
        usuarioBinder = new Binder<>(Usuario.class);
        modoNuevo = true;
        entrenador = new Entrenador();
        usuario = new Usuario();
        crearComponentesUI();
        loadEntrenadores();
    }

    private void crearComponentesUI() {
        imgFotoEntrenador.setVisible(false);
        imgFotoEntrenador.addStyleName("full-width");
        btnEliminar.setEnabled(false);
        bindEntrenador();
        addGridEntrenadoresSelectionListener();
        addBtnNuevoClickListener();
        addBtnGuardarClickListener();
        addBtnCancelarClickListener();
        addBtnEliminarClickListener();
        reiniciarFormulario();
    }

    private void addBtnEliminarClickListener() {
        btnEliminar.addClickListener((Button.ClickListener) event ->
                MessageBox.createError()
                        .withCaption("Confirmación")
                        .withMessage("¿Seguro que deseas eliminar al entrenador?")
                        .withAbortButton(ButtonOption.caption("Cancelar"))
                        .withIgnoreButton(
                                () -> {
                                    accesoEntrenador.eliminar(entrenador);
                                    reiniciarFormulario();
                                    gridEntrenadores.setItems(accesoEntrenador
                                            .obtenerTodos());
                                }, ButtonOption.caption("Aceptar"),
                                ButtonOption.focus(), ButtonOption.icon(null)
                        )
                        .open());
    }

    private void reiniciarFormulario() {
        entrenador = new Entrenador();
        usuario = new Usuario();
        modoNuevo = true;
        foto = null;
        gridEntrenadores.deselectAll();
        entrenadorBinder.setBean(entrenador);
        usuarioBinder.setBean(usuario);
        btnGuardar.setCaption("Insertar");
        btnEliminar.setEnabled(false);
        uploadFoto = new UploadFile("Foto de perfil:");
        uploadFoto.addStyleName("full-width");
        uploadFoto.setButtonCaption("Seleccionar foto");
        addUploadFotoClickListener();
        containerUploadFoto.removeAllComponents();
        containerUploadFoto.addComponent(uploadFoto);
        imgFotoEntrenador.setSource(null);
        imgFotoEntrenador.setVisible(false);
        tfEmail.setEnabled(true);
    }

    private void loadEntrenadores() {
        List<Entrenador> entrenadores = accesoEntrenador.obtenerTodos();
        gridEntrenadores.addStyleName("grid-list-with-image");
        gridEntrenadores.setItems(entrenadores);
        gridEntrenadores.removeAllColumns();
        gridEntrenadores.addColumn(entrenador ->
                        "<img src=\"http://localhost/uploads/"
                                + ((Entrenador) entrenador).getUrlFoto() + "\""
                                + "style=\"height: 100px; width: 100px;\" />",
                new HtmlRenderer()).setCaption("Fotografía");
        ((Grid.Column) gridEntrenadores.getColumns().get(0)).setWidth(136);
        gridEntrenadores.addColumn(entrenador -> "<span>"
                        + ((Entrenador) entrenador).getNombre() + " "
                        + ((Entrenador) entrenador).getApPaterno() + " "
                        + ((Entrenador) entrenador).getApMaterno() + "</span>",
                new HtmlRenderer()).setCaption("Nombre");
        gridEntrenadores.addColumn(entrenador -> "<span>"
                        + (((Entrenador) entrenador).getUsuario()
                        .getClaveVerificacion() == null ? "Activa" :
                        "Pendiente") + "</span>",
                new HtmlRenderer()).setCaption("Cuenta");
        gridEntrenadores.setResponsive(true);
    }

    private void bindEntrenador() {
        entrenadorBinder.setBean(entrenador);
        entrenadorBinder.forField(tfNombre).asRequired("El nombre es obligatorio")
                .withValidator(nombre -> nombre.length() >= 3,
                        "El nombre debe contener al menos 3 caracteres")
                .bind(Entrenador::getNombre, Entrenador::setNombre);
        entrenadorBinder.forField(tfApPaterno).asRequired("El primer apellido es " +
                "obligatorio")
                .withValidator(apPaterno -> apPaterno.length() >= 3,
                        "El nombre debe contener al menos 3 caracteres")
                .bind(Entrenador::getApPaterno, Entrenador::setApPaterno);
        entrenadorBinder.forField(tfApMaterno)
                .bind(Entrenador::getApMaterno, Entrenador::setApMaterno);
        entrenadorBinder.forField(tfTelefono).asRequired("El teléfono es obligatorio")
                .withValidator(telefono -> telefono.length() >= 10,
                        "El teléfono debe contener al menos 10 caracteres")
                .bind(Entrenador::getTelefono, Entrenador::setTelefono);
        entrenadorBinder.forField(dfFechaNacimiento).asRequired("La fecha es obligatoria")
                .withConverter(new LocalDateToDateConverter())
                .bind("fechaNacimiento");
        usuarioBinder.setBean(usuario);
        usuarioBinder.forField(tfEmail).asRequired("El email es obligatorio")
                .withValidator(new EmailValidator("El formato del email es " +
                        "incorrecto"))
                .bind("email");
    }

    private void addGridEntrenadoresSelectionListener() {
        gridEntrenadores.addSelectionListener((SelectionListener) event -> {
            if (!event.getAllSelectedItems().isEmpty()) {
                entrenador = (Entrenador) event.getFirstSelectedItem().get();
                entrenadorBinder.setBean(entrenador);
                usuario = entrenador.getUsuario();
                usuarioBinder.setBean(usuario);
                modoNuevo = false;
                btnGuardar.setCaption("Actualizar");
                btnEliminar.setEnabled(true);
                tfEmail.setEnabled(false);
            } else {
                reiniciarFormulario();
            }
        });
    }

    private void addUploadFotoClickListener() {
        uploadFoto.addFinishedListener((Upload.FinishedListener) event -> {
            imgFotoEntrenador.setVisible(true);
            foto = uploadFoto.getFile();
            imgFotoEntrenador.setSource(new FileResource(foto));
        });
    }

    private void addBtnGuardarClickListener() {
        btnGuardar.addClickListener((Button.ClickListener) event -> {
            boolean entrenadorValido = entrenadorBinder.validate().isOk();
            boolean usuarioValido = usuarioBinder.validate().isOk();
            if (entrenadorValido && usuarioValido) {
                if (modoNuevo && foto == null) {
                    Notification.show("Selecciona foto del entrenador",
                            Notification.Type.ERROR_MESSAGE);
                } else {
                    if (foto != null) {
                        foto.renameTo(new File("ent_"
                                + entrenador.getTelefono()));
                        foto = new File("ent_" + entrenador.getTelefono());
                        entrenador.setUrlFoto(foto.getName());
                        String response = new ImageUploader().uploadImage(foto);
                    }
                    entrenador.setUsuario(usuario);
                    if (modoNuevo) {
                        if (accesoEntrenador.insertar(entrenador)) {
                            Notification.show("Entrenador insertado " +
                                            "correctamente",
                                    Notification.Type.TRAY_NOTIFICATION);
                            usuario = new AccesoUsuario().getUsuarioByEmail
                                    (usuario.getEmail());
                            new Email().enviarEmailConfirmacion(usuario
                                            .getEmail(), entrenador.getNombre(),
                                    usuario.getClaveVerificacion());
                            reiniciarFormulario();
                        } else {
                            Notification.show("Error al insertar " +
                                            "entrenador",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        if (accesoEntrenador.actualizar(entrenador)) {
                            Notification.show("Entrenador actualizado " +
                                            "correctamente",
                                    Notification.Type.TRAY_NOTIFICATION);
                            reiniciarFormulario();
                        } else {
                            Notification.show("Error al actualizar " +
                                            "entrenador",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    }
                    gridEntrenadores.setItems(accesoEntrenador
                            .obtenerTodos());
                }
            } else {
                Notification.show("Verifica tus datos", Notification.Type
                        .ERROR_MESSAGE);
            }
        });
    }

    private void addBtnCancelarClickListener() {
        btnCancelar.addClickListener((Button.ClickListener) event -> {
            reiniciarFormulario();
        });
    }

    private void addBtnNuevoClickListener() {
        btnNuevo.addClickListener((Button.ClickListener) event -> {
            reiniciarFormulario();
            tfNombre.focus();
        });
    }
}
