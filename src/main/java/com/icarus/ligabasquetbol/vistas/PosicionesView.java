package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoPartido;
import com.icarus.ligabasquetbol.vistas.charts.PosicionEquiposChart;
import com.vaadin.addon.charts.Chart;
import org.vaadin.addon.borderlayout.BorderLayout;

public class PosicionesView extends BorderLayout {
    private AccesoPartido accesoPartido;

    public PosicionesView() {
        accesoPartido = new AccesoPartido();
        crearComponentes();
    }

    private void crearComponentes() {
        Chart posicionesChart = PosicionEquiposChart.create(accesoPartido
                .obtenerTodos());
        addComponents(posicionesChart);
    }
}
