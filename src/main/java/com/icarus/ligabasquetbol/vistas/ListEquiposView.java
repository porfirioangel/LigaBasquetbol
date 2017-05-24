package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEquipo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import org.vaadin.addon.borderlayout.BorderLayout;

public class ListEquiposView extends BorderLayout {
    private Grid<Equipo> gridEquipos;
    private Button btnNuevo;
    private Button btnEliminar;
    private Button btnVer;
    private Button btnModificar;
    private Equipo equipo;
    private AccesoEquipo accesoEquipo;
    private AccesoEntrenador accesoEntrenador;
    private AccesoJugador accesoJugador;

    public ListEquiposView() {
        accesoEquipo = new AccesoEquipo();
        accesoEntrenador = new AccesoEntrenador();
        accesoJugador = new AccesoJugador();
        crearComponentes();
    }

    private void crearComponentes() {
        setMargin(false);
        construirGridEquipos();
        btnNuevo = new Button("Agregar nuevo");
        btnNuevo.addStyleName("borderless");
        btnNuevo.addStyleName("color-text-blue");
        btnNuevo.setIcon(VaadinIcons.PLUS);
        addBtnNuevoClickListener();
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.addComponent(btnNuevo);
        topBar.setResponsive(true);
        btnEliminar = new Button("Eliminar");
        btnEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        btnEliminar.setEnabled(false);
        btnEliminar.setResponsive(true);
        addBtnEliminarClickListener();
        btnVer = new Button("Ver");
        btnVer.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnVer.setEnabled(false);
        btnVer.setResponsive(true);
        addBtnVerClickListener();
        btnModificar = new Button("Modificar");
        btnModificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnModificar.setEnabled(false);
        btnModificar.setResponsive(true);
        addBtnModificarClickListener();
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.addComponents(btnVer, btnModificar, btnEliminar);
        bottomBar.setResponsive(true);
        addComponent(topBar, Constraint.NORTH);
        addComponent(gridEquipos, Constraint.CENTER);
        addComponent(bottomBar, Constraint.SOUTH);
        setSizeFull();
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
        addGridEquiposSelectionListener();
    }

    private void addGridEquiposSelectionListener() {
        gridEquipos.addSelectionListener((SelectionListener) event -> {
            if (!event.getAllSelectedItems().isEmpty()) {
                equipo = (Equipo) event.getFirstSelectedItem().get();
                btnVer.setEnabled(true);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
            } else {
                equipo = null;
                btnVer.setEnabled(false);
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
            }
        });
    }

    private Window createEditEquipoWindow(String titulo, Equipo equipo) {
        Window equipoForm = new Window(titulo, new FormEquipoWindow(equipo));
        equipoForm.setSizeUndefined();
        equipoForm.setModal(true);
        equipoForm.setResizable(false);
        equipoForm.addStyleName("window-form");
        equipoForm.addCloseListener((Window.CloseListener) e -> {
            gridEquipos.setItems(accesoEquipo.obtenerTodos());
        });
        return equipoForm;
    }

    private void addBtnNuevoClickListener() {
        btnNuevo.addClickListener((Button.ClickListener) event -> {
            UI.getCurrent()
                    .addWindow(createEditEquipoWindow("Nuevo Equipo", null));
        });
    }

    private void addBtnModificarClickListener() {
        btnModificar.addClickListener((Button.ClickListener) event -> {
            UI.getCurrent()
                    .addWindow(createEditEquipoWindow("Editar Equipo", equipo));
        });
    }

    private void addBtnVerClickListener() {
        btnVer.addClickListener((Button.ClickListener) event -> {
            Window equipoForm = new Window("Detalles de Equipo",
                    new ViewEquipoWindow(equipo));
            equipoForm.setSizeUndefined();
            equipoForm.setModal(true);
            equipoForm.setResizable(false);
            equipoForm.addStyleName("window-form");
            UI.getCurrent().addWindow(equipoForm);
        });
    }

    private void addBtnEliminarClickListener() {
        btnEliminar.addClickListener((Button.ClickListener) event -> {
            MessageBox.createError()
                    .withCaption("Confirmación")
                    .withMessage("¿Seguro que deseas eliminar al equipo?")
                    .withAbortButton(ButtonOption.caption("Cancelar"))
                    .withIgnoreButton(() -> {
                                accesoEquipo.eliminar(equipo);
                                gridEquipos.setItems(accesoEquipo.obtenerTodos());
                            },
                            ButtonOption.caption("Aceptar"),
                            ButtonOption.focus(), ButtonOption.icon(null)
                    )
                    .open();
        });
    }
}
