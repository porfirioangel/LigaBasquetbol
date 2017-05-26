package com.icarus.ligabasquetbol.vistas.designs;

import com.icarus.ligabasquetbol.vistas.*;
import com.vaadin.ui.Button;

public class UsuarioDashboardScreen extends UsuarioDashboardDesign {
    public UsuarioDashboardScreen() {
        lblTituloVentana.setValue("Posiciones");
        contentPanel.addComponent(new PosicionesView());
        btnPosiciones.addStyleName("btn-dash-active");
        btnPosiciones.addClickListener((Button.ClickListener) event -> {
            btnPosiciones.addStyleName("btn-dash-active");
            btnPartidosFinalizados.removeStyleName("btn-dash-active");
            btnPartidosProximos.removeStyleName("btn-dash-active");
            lblTituloVentana.setValue("Posiciones");
            contentPanel.removeAllComponents();
            contentPanel.addComponent(new PosicionesView());
        });
        btnPartidosFinalizados.addClickListener((Button.ClickListener) event -> {
            btnPartidosFinalizados.addStyleName("btn-dash-active");
            btnPosiciones.removeStyleName("btn-dash-active");
            btnPartidosProximos.removeStyleName("btn-dash-active");
            lblTituloVentana.setValue("Partidos Finalizados");
            contentPanel.removeAllComponents();
            contentPanel.addComponent(new PartidosFinalizadosView());
        });
        btnPartidosProximos.addClickListener((Button.ClickListener) event -> {
            btnPartidosProximos.addStyleName("btn-dash-active");
            btnPosiciones.removeStyleName("btn-dash-active");
            btnPartidosFinalizados.removeStyleName("btn-dash-active");
            lblTituloVentana.setValue("Partidos Próximos");
            contentPanel.removeAllComponents();
            contentPanel.addComponent(new PartidosProximosView());
        });
    }
}
