package com.icarus.ligabasquetbol.logica;

import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;

public class EquipoPuntos implements Comparable<EquipoPuntos> {
    private Equipo equipo;
    private int puntos;

    public EquipoPuntos(Equipo equipo) {
        this.equipo = equipo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public int compareTo(EquipoPuntos o) {
        return -(puntos - o.puntos);
    }
}