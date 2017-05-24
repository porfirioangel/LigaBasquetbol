package com.icarus.ligabasquetbol.vistas;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ViewEquipoWindow extends VerticalLayout {
    private TextField tfNombreEquipo;
    private TextField tfNombreEntrenador;
    private ListSelect<Jugador> listJugadores;
    private AccesoJugador accesoJugador;
    private Equipo equipo;

    public ViewEquipoWindow(Equipo equipo) {
        this.equipo = equipo;
        accesoJugador = new AccesoJugador();
        crearComponentes();
    }

    private void crearComponentes() {
        tfNombreEquipo = new TextField("Equipo");
        tfNombreEquipo.setValue(equipo.getNombre());
        tfNombreEquipo.setSizeFull();
        tfNombreEntrenador = new TextField("Entrenador");
        tfNombreEntrenador.setValue(equipo.getEntrenador().getNombre());
        tfNombreEntrenador.setSizeFull();
        listJugadores = new ListSelect<>("Jugadores");
        listJugadores.setItems(accesoJugador.obtenerMiembros(equipo));
        listJugadores.setSizeFull();
        addComponents(tfNombreEquipo, tfNombreEntrenador, listJugadores);
    }
}
