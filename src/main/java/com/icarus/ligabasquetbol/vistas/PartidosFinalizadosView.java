package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoPartido;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.HtmlRenderer;
import org.vaadin.addon.borderlayout.BorderLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PartidosFinalizadosView extends BorderLayout {
    private AccesoPartido accesoPartido;
    private Grid<Partido> gridPartidos;
    private Label lblEtapaTorneo;

    public PartidosFinalizadosView() {
        accesoPartido = new AccesoPartido();
        crearComponentes();
    }

    private void crearComponentes() {
        setMargin(false);
        setSizeFull();
        List<Partido> partidos = accesoPartido.obtenerTodos();
        List<Partido> finalizados = new ArrayList<>();
        for (Partido partido : partidos) {
            if (partido.getFecha().compareTo(LocalDateTime.now()) < 0) {
                finalizados.add(partido);
            }
        }
        Notification.show("Hay " + finalizados.size() + " finalizados",
                Notification.Type.TRAY_NOTIFICATION);
        gridPartidos = new Grid<>(Partido.class);
        gridPartidos.addStyleName("grid-list-partidos");
        gridPartidos.setItems(finalizados);
        gridPartidos.removeAllColumns();
        gridPartidos.addColumn(partido -> "<strong>" + partido
                        .getEquipo1().getNombre() +
                        "</strong><br>" +
                        "<img src=\"http://148.217.200.108/liga_basquet/uploads/"
                        + partido.getEquipo1().getUrlLogo()
                        + "\" style=\"height: 100px; width: 100px;\" />"
                        + "<br><strong>" + partido.getPuntosE1()
                        + "</strong><br>",
                new HtmlRenderer()).setCaption("Equipo 1");
        ((Grid.Column) gridPartidos.getColumns().get(0)).setWidth(300);
        gridPartidos.addColumn(partido -> "<span>"
                        + partido.getFecha().format(DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm"))
                        + "</span>"
                        + "<br>(" + partido.getJornada() + ")",
                new HtmlRenderer()).setCaption("Fecha");
        gridPartidos.addColumn(partido -> "<strong>" + partido
                        .getEquipo2().getNombre() +
                        "</strong><br>" +
                        "<img src=\"http://148.217.200.108/liga_basquet/uploads/"
                        + partido.getEquipo2().getUrlLogo()
                        + "\" style=\"height: 100px; width: 100px;\" />"
                        + "<br><strong>" + partido.getPuntosE2()
                        + "</strong><br>",
                new HtmlRenderer()).setCaption("Equipo 2");
        ((Grid.Column) gridPartidos.getColumns().get(2)).setWidth(300);
        gridPartidos.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridPartidos.setResponsive(true);
        gridPartidos.setSizeFull();
        if (finalizados.size() > 0) {
            lblEtapaTorneo = new Label("<span>Estado del torneo: <strong>"
                    + partidos.get(partidos.size() - 1).getJornada()
                    + "</strong></span>", ContentMode.HTML);
            addComponent(gridPartidos, Constraint.CENTER);
        } else {
            lblEtapaTorneo = new Label("<strong>No hay partidos " +
                    "finalizados</strong>", ContentMode.HTML);
        }
        addComponent(lblEtapaTorneo, Constraint.NORTH);
    }
}
