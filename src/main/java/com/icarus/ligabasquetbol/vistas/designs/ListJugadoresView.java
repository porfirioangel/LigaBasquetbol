package com.icarus.ligabasquetbol.vistas.designs;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.icarus.ligabasquetbol.conectividad.ImageUploader;
import com.icarus.ligabasquetbol.vistas.components.UploadFile;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.event.selection.SelectionEvent;
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

public class ListJugadoresView extends ListJugadoresDesign {
    private AccesoJugador accesoJugador;
    private Binder<Jugador> binder;
    private UploadFile uploadFoto;
    private Jugador jugador;
    private boolean modoNuevo;
    private File foto;

    public ListJugadoresView() {
        accesoJugador = new AccesoJugador();
        binder = new Binder<>(Jugador.class);
        modoNuevo = true;
        jugador = new Jugador();
        crearComponentesUI();
        loadJugadores();
    }

    private void crearComponentesUI() {
        imgFotoJugador.setVisible(false);
        imgFotoJugador.addStyleName("full-width");
        btnEliminar.setEnabled(false);
        bindJugador();
        addGridJugadoresSelectionListener();
        addBtnNuevoClickListener();
        addBtnGuardarClickListener();
        addBtnCancelarClickListener();
        addBtnEliminarClickListener();
        reiniciarFormulario();
    }

    private void addBtnEliminarClickListener() {
        btnEliminar.addClickListener((Button.ClickListener) event -> {
            MessageBox.createError()
                    .withCaption("Confirmación")
                    .withMessage("¿Seguro que deseas eliminar al usuario?")
                    .withAbortButton(ButtonOption.caption("Cancelar"))
                    .withIgnoreButton(
                            () -> {
                                accesoJugador.eliminar(jugador);
                                reiniciarFormulario();
                                gridJugadores.setItems(accesoJugador
                                        .obtenerTodos());
                            }, ButtonOption.caption("Aceptar"),
                            ButtonOption.focus(), ButtonOption.icon(null)
                    )
                    .open();

        });
    }

    private void reiniciarFormulario() {
        jugador = new Jugador();
        modoNuevo = true;
        foto = null;
        gridJugadores.deselectAll();
        binder.setBean(jugador);
        btnGuardar.setCaption("Insertar");
        btnEliminar.setEnabled(false);
        uploadFoto = new UploadFile("Foto de perfil:");
        uploadFoto.addStyleName("full-width");
        uploadFoto.setButtonCaption("Seleccionar foto");
        addUploadFotoClickListener();
        containerUploadFoto.removeAllComponents();
        containerUploadFoto.addComponent(uploadFoto);
        imgFotoJugador.setSource(null);
        imgFotoJugador.setVisible(false);
    }

    private void loadJugadores() {
        List<Jugador> jugadores = accesoJugador.obtenerTodos();
        gridJugadores.addStyleName("grid-list-with-image");
        gridJugadores.setItems(jugadores);
        gridJugadores.removeAllColumns();
        gridJugadores.addColumn(jugador ->
                        "<img src=\"http://148.217.200.108/liga_basquet/uploads/"
                                + ((Jugador) jugador).getUrlFoto() + "\""
                                + "style=\"height: 100px; width: 100px;\" />",
                new HtmlRenderer()).setCaption("Fotografía");
        ((Grid.Column) gridJugadores.getColumns().get(0)).setWidth(136);
        gridJugadores.addColumn(jugador -> "<span>"
                        + ((Jugador) jugador).getNombre() + " "
                        + ((Jugador) jugador).getApPaterno() + " "
                        + ((Jugador) jugador).getApMaterno() + "</span>",
                new HtmlRenderer()).setCaption("Nombre");
        gridJugadores.setResponsive(true);
    }

    private void bindJugador() {
        binder.setBean(jugador);
        binder.forField(tfNombre).asRequired("El nombre es obligatorio")
                .withValidator(nombre -> nombre.length() >= 3,
                        "El nombre debe contener al menos 3 caracteres")
                .bind(Jugador::getNombre, Jugador::setNombre);
        binder.forField(tfApPaterno).asRequired("El primer apellido es " +
                "obligatorio")
                .withValidator(apPaterno -> apPaterno.length() >= 3,
                        "El nombre debe contener al menos 3 caracteres")
                .bind(Jugador::getApPaterno, Jugador::setApPaterno);
        binder.forField(tfApMaterno)
                .bind(Jugador::getApMaterno, Jugador::setApMaterno);
        binder.forField(tfTelefono).asRequired("El teléfono es obligatorio")
                .withValidator(telefono -> telefono.length() >= 10,
                        "El teléfono debe contener al menos 10 caracteres")
                .bind(Jugador::getTelefono, Jugador::setTelefono);
        binder.forField(dfFechaNacimiento).asRequired("La fecha es obligatoria")
                .withConverter(new LocalDateToDateConverter())
                .bind("fechaNacimiento");
    }

    private void addGridJugadoresSelectionListener() {
        gridJugadores.addSelectionListener(new SelectionListener() {
            @Override
            public void selectionChange(SelectionEvent event) {
                if (!event.getAllSelectedItems().isEmpty()) {
                    jugador = (Jugador) event.getFirstSelectedItem().get();
                    binder.setBean(jugador);
                    modoNuevo = false;
                    btnGuardar.setCaption("Actualizar");
                    btnEliminar.setEnabled(true);
                } else {
                    reiniciarFormulario();
                }
            }
        });
    }

    private void addUploadFotoClickListener() {
        uploadFoto.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent event) {
                imgFotoJugador.setVisible(true);
                foto = uploadFoto.getFile();
                imgFotoJugador.setSource(new FileResource(foto));
            }
        });
    }

    private void addBtnGuardarClickListener() {
        btnGuardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (binder.validate().isOk()) {
                    if (modoNuevo && foto == null) {
                        Notification.show("Selecciona foto del jugador",
                                Notification.Type.ERROR_MESSAGE);
                    } else {
                        if (foto != null) {
                            foto.renameTo(new File("jug_" + jugador.getTelefono()));
                            foto = new File("jug_" + jugador.getTelefono());
                            jugador.setUrlFoto(foto.getName());
                            String response = new ImageUploader().uploadImage(foto);
                        }
                        if (modoNuevo) {
                            if (accesoJugador.insertar(jugador)) {
                                Notification.show("Jugador insertado " +
                                                "correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
                                reiniciarFormulario();
                            } else {
                                Notification.show("Error al insertar usuario",
                                        Notification.Type.ERROR_MESSAGE);
                            }
                        } else {
                            if (accesoJugador.actualizar(jugador)) {
                                Notification.show("Jugador actualizado " +
                                                "correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
                                reiniciarFormulario();
                            } else {
                                Notification.show("Error al actualizar usuario",
                                        Notification.Type.ERROR_MESSAGE);
                            }
                        }
                        gridJugadores.setItems(accesoJugador.obtenerTodos());
                    }
                } else {
                    Notification.show("Verifica tus datos", Notification.Type
                            .ERROR_MESSAGE);
                }
            }
        });
    }

    private void addBtnCancelarClickListener() {
        btnCancelar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                reiniciarFormulario();
            }
        });
    }

    private void addBtnNuevoClickListener() {
        btnNuevo.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                reiniciarFormulario();
                tfNombre.focus();
            }
        });
    }
}
