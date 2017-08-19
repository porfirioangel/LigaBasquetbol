package com.icarus.ligabasquetbol.utils;

import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;

public class DataStructureConverter {
    public static Jugador[] toJugadorArray(Object[] array) {
        Jugador[] jugadores = new Jugador[array.length];
        for (int i = 0; i < array.length; i++) {
            jugadores[i] = (Jugador) array[i];
        }
        return jugadores;
    }
}
