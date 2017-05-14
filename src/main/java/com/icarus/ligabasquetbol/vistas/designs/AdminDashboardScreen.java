package com.icarus.ligabasquetbol.vistas.designs;

import com.vaadin.ui.Button;

public class AdminDashboardScreen extends AdminDashboardDesign {
    public AdminDashboardScreen() {
        lblTituloVentana.setValue("Jugadores");
        contentPanel.addComponent(new ListJugadoresView());
        btnJugadores.addStyleName("btn-dash-active");
        btnJugadores.addClickListener((Button.ClickListener) event -> {
            btnJugadores.addStyleName("btn-dash-active");
            btnEntrenadores.removeStyleName("btn-dash-active");
            btnPartidos.removeStyleName("btn-dash-active");
            lblTituloVentana.setValue("Jugadores");
            contentPanel.removeAllComponents();
            contentPanel.addComponent(new ListJugadoresView());
        });
        btnEntrenadores.addClickListener((Button.ClickListener) event -> {
            btnJugadores.removeStyleName("btn-dash-active");
            btnEntrenadores.addStyleName("btn-dash-active");
            btnPartidos.removeStyleName("btn-dash-active");
            lblTituloVentana.setValue("Entrenadores");
            contentPanel.removeAllComponents();
        });
        btnPartidos.addClickListener((Button.ClickListener) event -> {
            btnJugadores.removeStyleName("btn-dash-active");
            btnEntrenadores.removeStyleName("btn-dash-active");
            btnPartidos.addStyleName("btn-dash-active");
            lblTituloVentana.setValue("Partidos");
            contentPanel.removeAllComponents();
        });
    }
}
