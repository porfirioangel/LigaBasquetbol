package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEquipo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Entrenador;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.addon.borderlayout.BorderLayout;

import java.util.List;

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
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.addComponent(btnNuevo);
        topBar.setResponsive(true);
        btnEliminar = new Button("Eliminar");
        btnEliminar.addStyleName(ValoTheme.BUTTON_DANGER);
        btnEliminar.setEnabled(false);
        btnEliminar.setResponsive(true);
        btnVer = new Button("Ver");
        btnVer.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnVer.setEnabled(false);
        btnVer.setResponsive(true);
        btnModificar = new Button("Modificar");
        btnModificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnModificar.setEnabled(false);
        btnModificar.setResponsive(true);
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
    }
}
