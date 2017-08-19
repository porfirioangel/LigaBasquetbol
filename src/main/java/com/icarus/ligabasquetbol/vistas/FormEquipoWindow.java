package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.conectividad.ImageUploader;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEquipo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Entrenador;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.icarus.ligabasquetbol.vistas.components.UploadFile;
import com.vaadin.data.Binder;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.Set;

public class FormEquipoWindow extends VerticalLayout {
    private TextField tfNombre;
    private UploadFile uploadFile;
    private TwinColSelect<Jugador> selectorJugadores;
    private ComboBox<Entrenador> cbxEntrenadores;
    private Button btnGuardar;
    private AccesoJugador accesoJugador;
    private AccesoEntrenador accesoEntrenador;
    private File foto;
    private Image imgFotoEquipo;
    private AccesoEquipo accesoEquipo;
    private Equipo equipo;
    private boolean modoNuevo;
    private Binder<Equipo> equipoBinder;

    public FormEquipoWindow(Equipo equipo) {
        accesoEntrenador = new AccesoEntrenador();
        accesoJugador = new AccesoJugador();
        accesoEquipo = new AccesoEquipo();
        if (equipo == null) {
            modoNuevo = true;
            this.equipo = new Equipo();
        } else {
            this.equipo = equipo;
        }
        equipoBinder = new Binder<>(Equipo.class);
        crearComponentes();
        bindEquipo();
    }

    public FormEquipoWindow() {
        this(null);
    }

    private void crearComponentes() {
        setSizeUndefined();
        setMargin(true);
        setSpacing(true);
        setResponsive(true);
        addStyleName("window-form");
        tfNombre = new TextField("Nombre");
        tfNombre.setSizeFull();
        selectorJugadores = new TwinColSelect<>("Jugadores");
        selectorJugadores.setLeftColumnCaption("Disponibles");
        selectorJugadores.setRightColumnCaption("Asignados");
        selectorJugadores.setResponsive(true);
        selectorJugadores.setSizeFull();
        selectorJugadores.setRows(5);
        if(!modoNuevo) {
            new AccesoJugador().quitarJugadoresDeEquipo(equipo);
        }
        selectorJugadores.setItems(accesoJugador.obtenerDisponibles());
        // Esto no lo implemento porque el TwinColSelect ya no responde
        // adecuadamente cuando selecciono elementos en código, después ya no
        // se identifica cuáles de ellos son removidos, si alguien quiere
        // editar un equipo, deberá colocar de nuevo todos sus jugadores
//        if (modoNuevo) {
//            selectorJugadores.setItems(accesoJugador.obtenerDisponibles());
//        } else {
//            selectorJugadores.setItems(accesoJugador.obtenerTodos());
//            Jugador[] miembros = DataStructureConverter.toJugadorArray
//                    (accesoJugador.obtenerMiembros(equipo).toArray());
//            selectorJugadores.select(miembros);
//        }
        cbxEntrenadores = new ComboBox<>("Entrenador");
        cbxEntrenadores.setItems(accesoEntrenador.obtenerDisponibles());
        cbxEntrenadores.setSizeFull();
        uploadFile = new UploadFile("Logotipo");
        uploadFile.setButtonCaption("Elegir logotipo");
        imgFotoEquipo = new Image();
        imgFotoEquipo.setVisible(false);
        imgFotoEquipo.addStyleName("logo-equipo");
        addUploadFotoClickListener();
        btnGuardar = new Button(modoNuevo ? "Insertar" : "Actualizar");
        btnGuardar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addBtnGuardarClickListener();
        addComponents(tfNombre, selectorJugadores, cbxEntrenadores,
                uploadFile, imgFotoEquipo, btnGuardar);
    }

    private void bindEquipo() {
        System.out.println(">>> EquipoBinded: " + equipo);
        equipoBinder.setBean(equipo);
        equipoBinder.forField(tfNombre).asRequired("El nombre es obligatorio")
                .withValidator(nombre -> nombre.length() >= 3,
                        "El nombre debe contener al menos 3 caracteres")
                .bind(Equipo::getNombre, Equipo::setNombre);
        equipoBinder.forField(cbxEntrenadores).asRequired(
                "El entrenador es requerido")
                .bind(Equipo::getEntrenador, Equipo::setEntrenador);
    }

    private void addUploadFotoClickListener() {
        uploadFile.addFinishedListener((Upload.FinishedListener) event -> {
            foto = uploadFile.getFile();
            imgFotoEquipo.setVisible(true);
            imgFotoEquipo.setSource(new FileResource(foto));
        });
    }

    private void addBtnGuardarClickListener() {
        btnGuardar.addClickListener((Button.ClickListener) event -> {
            boolean equipoValido = equipoBinder.validate().isOk();
            if (equipoValido) {
                if (modoNuevo && foto == null) {
                    Notification.show("Selecciona el logo del equipo",
                            Notification.Type.ERROR_MESSAGE);
                } else {
                    if (foto != null) {
                        foto.renameTo(new File("equ_" + equipo.getNombre()));
                        equipo.setUrlLogo("equ_" + equipo.getNombre());
                        foto = new File(equipo.getUrlLogo());
                        System.out.println(">>> Logo: " + foto.getAbsolutePath());
                        String response = new ImageUploader().uploadImage(foto);
                    }
                    if (modoNuevo) {
                        if (accesoEquipo.insertar(equipo)) {
                            if (actualizarMiembrosEquipo()) {
                                Notification.show("Equipo insertado correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
                                ((Window) getParent()).close();
                            }
                        } else {
                            Notification.show("Error al insertar equipo",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        if (accesoEquipo.actualizar(equipo)) {
                            if (actualizarMiembrosEquipo()) {
                                Notification.show("Equipo actualizado " +
                                                "correctamente",
                                        Notification.Type.TRAY_NOTIFICATION);
                                ((Window) getParent()).close();
                            }
                        } else {
                            Notification.show("Error al actualizar equipo",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    private boolean actualizarMiembrosEquipo() {
        Set<Jugador> jugadores = selectorJugadores.getSelectedItems();
        Equipo equipoObtenido = accesoEquipo.obtenerEquipoPorNombre(equipo
                .getNombre());
        if (!modoNuevo) {
            int updated = accesoJugador.quitarJugadoresDeEquipo(equipoObtenido);
            System.out.println(">>> JugadoresRemovidosEquipo: " + updated);
        }
        System.out.println(">>> Seleccionados: " + jugadores.toString());
        for (Jugador jugador : jugadores) {
            jugador.setEquipo(equipoObtenido);
            if (!accesoJugador.actualizar(jugador)) {
                return false;
            }
        }
        return true;
    }
}

