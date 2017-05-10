package com.icarus.ligabasquetbol.vistas.designs;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.icarus.ligabasquetbol.utils.ImageUploader;
import com.icarus.ligabasquetbol.vistas.UploadFile;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.ImageRenderer;

import java.io.File;
import java.util.List;

/**
 * Created by porfirio on 5/10/17.
 */
public class ListJugadoresView extends ListJugadoresDesign {
    private AccesoJugador accesoJugador;
    private Jugador jugador;
    private Binder<Jugador> binder;
    private UploadFile uploadFoto;
    private boolean modoNuevo;
    private final String BASE_URL = "http://localhost/uploads/";

    public ListJugadoresView() {
        accesoJugador = new AccesoJugador();
        binder = new Binder<>(Jugador.class);
        modoNuevo = true;
        jugador = new Jugador();
        imgFotoJugador.setVisible(false);
        imgFotoJugador.addStyleName("full-width");
        uploadFoto = new UploadFile("Foto de perfil:");
        containerUploadFoto.addComponent(uploadFoto);
        uploadFoto.addStyleName("full-width");
        uploadFoto.setButtonCaption("Seleccionar foto");
        loadJugadores();
        addGridJugadoresSelectionListener();
        bindJugador();
        addUploadFotoClickListener();
        addBtnNuevoClickListener();
        addBtnGuardarClickListener();
    }

    private void addBtnNuevoClickListener() {
        btnNuevo.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                modoNuevo = true;
                refreshBtnGuardarCaption();
                gridJugadores.deselectAll();
                tfNombre.focus();
            }
        });
    }

    private void loadJugadores() {
        List<Jugador> jugadores = accesoJugador.obtenerTodos();
        gridJugadores.addStyleName("grid-jugadores");
        gridJugadores.setItems(jugadores);
        gridJugadores.removeAllColumns();
        gridJugadores.addColumn(jugador ->
                        "<img src=\"http://localhost/uploads/"
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

    private void addGridJugadoresSelectionListener() {
        gridJugadores.addSelectionListener(new SelectionListener() {
            @Override
            public void selectionChange(SelectionEvent event) {
                if (!event.getAllSelectedItems().isEmpty()) {
                    jugador = (Jugador) event.getFirstSelectedItem().get();
                    binder.setBean(jugador);
                    modoNuevo = false;
                    refreshBtnGuardarCaption();
                } else {
                    jugador = new Jugador();
                    binder.setBean(jugador);
                    modoNuevo = true;
                    refreshBtnGuardarCaption();
                }
            }
        });
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

    private void addUploadFotoClickListener() {
        uploadFoto.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent event) {
                imgFotoJugador.setVisible(true);
                imgFotoJugador.setSource(new FileResource(uploadFoto.getFile()));
            }
        });
    }

    private void refreshBtnGuardarCaption() {
        if (modoNuevo)
            btnGuardar.setCaption("Insertar");
        else
            btnGuardar.setCaption("Actualizar");
    }

    private void addBtnGuardarClickListener() {
        btnGuardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (binder.validate().isOk()) {
                    if (imgFotoJugador.getSource() == null) {
                        Notification.show("Selecciona foto del jugador",
                                Notification.Type.ERROR_MESSAGE);
                    } else {
                        File foto = uploadFoto.getFile();
                        foto.renameTo(new File("jug_" + jugador.getTelefono()));
                        foto = new File("jug_" + jugador.getTelefono());
                        jugador.setUrlFoto(foto.getName());
                        String response = new ImageUploader().uploadImage(foto);
                        Notification.show("Response: " + response, Notification
                                .Type.HUMANIZED_MESSAGE);

                        if (modoNuevo) {
                            if (accesoJugador.insertar(jugador)) {
                                Notification.show("Jugador insertado " +
                                                "correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
                            } else {
                                Notification.show("Error al insertar usuario",
                                        Notification.Type.ERROR_MESSAGE);
                            }
                        } else {
                            if (accesoJugador.actualizar(jugador)) {
                                Notification.show("Jugador actualizado " +
                                                "correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
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
}
