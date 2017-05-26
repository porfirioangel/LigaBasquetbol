package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.logica.GeneracionTorneo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEquipo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoPartido;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.ui.NumberField;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListPartidosView extends BorderLayout {
    private AccesoPartido accesoPartido;
    private AccesoEquipo accesoEquipo;
    private List<Partido> partidos;

    public ListPartidosView() {
        accesoPartido = new AccesoPartido();
        accesoEquipo = new AccesoEquipo();
        partidos = accesoPartido.obtenerTodos();
        crearComponentes();
        setSizeFull();
    }

    private void crearComponentes() {
        setMargin(false);
        if (partidos.size() == 0) {
            new NoPartidosView().crearComponentes();
        } else {
            new PartidosView().crearComponentes();
        }
    }

    private class NoPartidosView {
        private Label lblNoPartidos;
        private Button btnCrearTorneo;
        private TextField tfNombre;
        private DateField dfFechaInicio;
        private Grid<Equipo> gridEquipos;
        private Button btnGenerarTorneo;

        public void crearComponentes() {
            lblNoPartidos = new Label("No hay partidos actualmente, te " +
                    "invitamos a crear un nuevo torneo");
            btnCrearTorneo = new Button("Crear torneo");
            btnCrearTorneo.addStyleName(ValoTheme.BUTTON_PRIMARY);
            addBtnCrearTorneoClickListener();
            VerticalLayout vlNoPartido = new VerticalLayout();
            vlNoPartido.setMargin(false);
            vlNoPartido.addComponents(lblNoPartidos, btnCrearTorneo);
            addComponent(vlNoPartido, Constraint.NORTH);
        }

        private void addBtnCrearTorneoClickListener() {
            btnCrearTorneo.addClickListener((Button.ClickListener) event -> {
                removeComponent(Constraint.CENTER);
                removeComponent(Constraint.SOUTH);
                removeComponent(Constraint.NORTH);
                tfNombre = new TextField();
                construirGridEquipos();
                dfFechaInicio = new DateField();
                btnGenerarTorneo = new Button("Generar torneo");
                btnGenerarTorneo.addStyleName(ValoTheme.BUTTON_PRIMARY);
                addBtnGenerarTorneoClickListener();
                HorizontalLayout hlDatosTorneo = new HorizontalLayout();
                hlDatosTorneo.setMargin(false);
                hlDatosTorneo.addComponents(new Label("*Nombre del torneo:"),
                        tfNombre, new Label("*Fecha de inicio:"),
                        dfFechaInicio);
                VerticalLayout vlEquipos = new VerticalLayout();
                vlEquipos.setMargin(false);
                vlEquipos.addComponent(gridEquipos);
                HorizontalLayout hlBotones = new HorizontalLayout();
                hlBotones.setMargin(false);
                hlBotones.addComponent(btnGenerarTorneo);
                addComponent(hlDatosTorneo, Constraint.NORTH);
                addComponent(vlEquipos, Constraint.CENTER);
                addComponent(hlBotones, Constraint.SOUTH);
            });
        }

        private void construirGridEquipos() {
            gridEquipos = new Grid<>(Equipo.class);
            gridEquipos.addStyleName("grid-list-with-image");
            gridEquipos.setItems(accesoEquipo.obtenerTodos());
            gridEquipos.removeAllColumns();
            gridEquipos.addColumn(equipo -> "<img src=\"http://localhost/uploads/"
                    + equipo.getUrlLogo() + "\" style=\"height: 100px; " +
                    "width: 100px;\" />", new HtmlRenderer())
                    .setCaption("Logotipo");
            ((Grid.Column) gridEquipos.getColumns().get(0)).setWidth(136);
            gridEquipos.addColumn("nombre").setCaption("Nombre");
            gridEquipos.setSelectionMode(Grid.SelectionMode.SINGLE);
            gridEquipos.setResponsive(true);
            gridEquipos.setSizeFull();
        }

        private void addBtnGenerarTorneoClickListener() {
            btnGenerarTorneo.addClickListener((Button.ClickListener) event -> {
                if (!tfNombre.isEmpty() && !dfFechaInicio.isEmpty()) {
                    MessageBox.createQuestion()
                            .withCaption("Confirmación")
                            .withMessage("¿Seguro de que deseas crear el torneo?")
                            .withAbortButton(ButtonOption.caption("Cancelar"))
                            .withIgnoreButton(
                                    () -> {
                                        partidos = new GeneracionTorneo(tfNombre
                                                .getValue(), dfFechaInicio
                                                .getValue().atTime(15, 0),
                                                accesoEquipo.obtenerTodos())
                                                .GenerarTorneo();
                                        accesoPartido.insertar(partidos);
                                        removeComponent(Constraint.CENTER);
                                        removeComponent(Constraint.SOUTH);
                                        removeComponent(Constraint.NORTH);
                                        new PartidosView().crearComponentes();
                                    }, ButtonOption.caption("Aceptar"),
                                    ButtonOption.focus(), ButtonOption.icon(null)
                            )
                            .open();
                } else {
                    Notification.show("Error", "No debes dejar campos vacíos",
                            Notification.Type.ERROR_MESSAGE);
                }
            });
        }

    }

    private class PartidosView {
        private Grid<Partido> gridPartidos;
        private Button btnActualizarMarcador;
        private Button btnFinalizarTorneo;
        private Partido partido;

        public void crearComponentes() {
            construirGridPartidos();
            btnActualizarMarcador = new Button("Actualizar marcador");
            btnActualizarMarcador.addStyleName(ValoTheme.BUTTON_PRIMARY);
            btnActualizarMarcador.setEnabled(false);
            addBtnActualizarMarcadorClickListener();
            btnFinalizarTorneo = new Button("Finalizar torneo");
            btnFinalizarTorneo.addStyleName(ValoTheme.BUTTON_DANGER);
            addBtnFinalizarTorneoClickListener();
            HorizontalLayout hlBotones = new HorizontalLayout();
            hlBotones.addComponents(btnActualizarMarcador, btnFinalizarTorneo);
            addComponent(gridPartidos, Constraint.CENTER);
            addComponent(hlBotones, Constraint.SOUTH);
        }

        private void construirGridPartidos() {
            gridPartidos = new Grid<>(Partido.class);
            gridPartidos.addStyleName("grid-list-partidos");
            gridPartidos.setItems(accesoPartido.obtenerTodos());
            gridPartidos.removeAllColumns();
            gridPartidos.addColumn(partido -> "<strong>" + partido
                            .getEquipo1().getNombre() +
                            "</strong><br>" +
                            "<img src=\"http://localhost/uploads/"
                            + partido.getEquipo1().getUrlLogo()
                            + "\" style=\"height: 100px; width: 100px;\" />"
                            + "<br><strong>" + partido.getPuntosE1()
                            + "</strong><br>",
                    new HtmlRenderer()).setCaption("Equipo 1");
            ((Grid.Column) gridPartidos.getColumns().get(0)).setWidth(300);
            gridPartidos.addColumn(partido -> "<span>"
                            + partido.getFecha().format(DateTimeFormatter
                            .ofPattern("dd/MM/yyyy HH:mm"))
                            + "</span>",
                    new HtmlRenderer()).setCaption("Fecha");
            gridPartidos.addColumn(partido -> "<strong>" + partido
                            .getEquipo2().getNombre() +
                            "</strong><br>" +
                            "<img src=\"http://localhost/uploads/"
                            + partido.getEquipo2().getUrlLogo()
                            + "\" style=\"height: 100px; width: 100px;\" />"
                            + "<br><strong>" + partido.getPuntosE2()
                            + "</strong><br>",
                    new HtmlRenderer()).setCaption("Equipo 2");
            ((Grid.Column) gridPartidos.getColumns().get(2)).setWidth(300);
            gridPartidos.setSelectionMode(Grid.SelectionMode.SINGLE);
            gridPartidos.setResponsive(true);
            gridPartidos.setSizeFull();
            addGridPartidosSelectionListener();
        }

        private void addGridPartidosSelectionListener() {
            gridPartidos.addSelectionListener((SelectionListener<Partido>) event -> {
                if (!event.getAllSelectedItems().isEmpty()) {
                    btnActualizarMarcador.setEnabled(true);
                    partido = event.getFirstSelectedItem().get();
                } else {
                    btnActualizarMarcador.setEnabled(false);
                    partido = null;
                }
            });
        }

        private void addBtnFinalizarTorneoClickListener() {
            btnFinalizarTorneo.addClickListener((Button.ClickListener) event -> {
                MessageBox.createError()
                        .withCaption("Confirmación")
                        .withMessage("¿Seguro que deseas terminar el " +
                                "torneo? Ya no se podrán ver los resultados " +
                                "ni las puntuaciones.")
                        .withAbortButton(ButtonOption.caption("Cancelar"))
                        .withIgnoreButton(
                                () -> {
                                    accesoPartido.vaciarPartidos();
                                    removeComponent(Constraint.CENTER);
                                    removeComponent(Constraint.SOUTH);
                                    new NoPartidosView().crearComponentes();
                                }, ButtonOption.caption("Aceptar"),
                                ButtonOption.focus(), ButtonOption.icon(null)
                        )
                        .open();
            });
        }

        private void addBtnActualizarMarcadorClickListener() {
            btnActualizarMarcador.addClickListener((Button.ClickListener) event -> {
                FormLayout flMarcador = new FormLayout();
                NumberField nfPuntosE1 = new NumberField("Puntos equipo 1:");
                flMarcador.setMargin(true);
                nfPuntosE1.setDecimalAllowed(false);
                nfPuntosE1.setMinValue(0);
                nfPuntosE1.setMaxValue(1000);
                nfPuntosE1.setGroupingSeparator('0');
                nfPuntosE1.setSizeFull();
                NumberField nfPuntosE2 = new NumberField("Puntos equipo 2:");
                nfPuntosE2.setDecimalAllowed(false);
                nfPuntosE2.setMinValue(0);
                nfPuntosE2.setMaxValue(1000);
                nfPuntosE2.setSizeFull();
                nfPuntosE2.setGroupingSeparator('0');
                Button btnGuardarMarcador = new Button("Guardar");
                btnGuardarMarcador.addStyleName(ValoTheme.BUTTON_PRIMARY);
                btnGuardarMarcador.setSizeFull();
                btnGuardarMarcador.addClickListener((Button.ClickListener) event1 -> {
                    if (!nfPuntosE1.isEmpty() && !nfPuntosE2.isEmpty()) {
                        partido.setPuntosE1(Integer.parseInt(nfPuntosE1
                                .getValue()));
                        partido.setPuntosE2(Integer.parseInt(nfPuntosE2
                                .getValue()));
                        if (accesoPartido.actualizarMarcador(partido)) {
                            Notification.show("Marcador actualizado",
                                    Notification.Type.TRAY_NOTIFICATION);
                            gridPartidos.setItems(accesoPartido.obtenerTodos());
                            ((Window) flMarcador.getParent()).close();
                        }
                    } else {
                        Notification.show("Error", "No puedes dejar campos " +
                                "vacíos", Notification.Type.ERROR_MESSAGE);
                    }
                });
                flMarcador.addComponents(nfPuntosE1, nfPuntosE2,
                        btnGuardarMarcador);
                Window equipoForm = new Window("Marcador del partido",
                        flMarcador);
                equipoForm.setSizeUndefined();
                equipoForm.setModal(true);
                equipoForm.setResizable(false);
                equipoForm.addStyleName("window-form");
                equipoForm.setWidth("400px");
                UI.getCurrent().addWindow(equipoForm);
            });
        }
    }
}