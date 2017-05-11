package com.icarus.ligabasquetbol.vistas.designs;

import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;

import java.io.File;

public class DashboardAdminScreen extends DashboardAdminDesign {
    public DashboardAdminScreen() {
        lblTituloVentana.setValue("Jugadores");
        contentPanel.addComponent(new ListJugadoresView());
        imgLogo.setSource(new ThemeResource("logo.png"));
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
//            contentPanel.addComponent(null);
        });
        btnPartidos.addClickListener((Button.ClickListener) event -> {
            btnJugadores.removeStyleName("btn-dash-active");
            btnEntrenadores.removeStyleName("btn-dash-active");
            btnPartidos.addStyleName("btn-dash-active");
            lblTituloVentana.setValue("Partidos");
            contentPanel.removeAllComponents();
//            contentPanel.addComponent(null);
        });
    }
}
