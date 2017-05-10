package com.icarus.ligabasquetbol.vistas.designs;

public class DashboardAdminScreen extends DashboardAdminDesign {
    public DashboardAdminScreen() {
        lblTituloVentana.setValue("Jugadores");
        contentPanel.addComponent(new ListJugadoresView());
    }
}
