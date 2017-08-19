package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoPartido;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;
import com.icarus.ligabasquetbol.vistas.charts.PosicionEquiposChart;
import com.vaadin.addon.charts.Chart;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import org.vaadin.addon.borderlayout.BorderLayout;

import java.util.List;

public class PosicionesView extends BorderLayout {
    private AccesoPartido accesoPartido;

    public PosicionesView() {
        accesoPartido = new AccesoPartido();
        crearComponentes();
    }

    private void crearComponentes() {
        setMargin(false);
        setSizeFull();
        List<Partido> partidos = accesoPartido.obtenerTodos();
        if (partidos.size() > 0) {
            Chart posicionesChart = PosicionEquiposChart.create(accesoPartido
                    .obtenerTodos());
            addComponent(posicionesChart, Constraint.NORTH);
        } else {
            addComponent(new Label("<strong>No hay ningún torneo en este " +
                    "momento</strong>", ContentMode.HTML), Constraint.NORTH);
        }
    }
}
