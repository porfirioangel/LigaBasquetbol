package com.icarus.ligabasquetbol.persistencia.modelos;

import java.time.LocalDateTime;

public class Partido {
    private int id;
    private Equipo equipo1;
    private Equipo equipo2;
    private LocalDateTime fecha;
    private String torneo;
    private String jornada;
    private int puntosE1;
    private int puntosE2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTorneo() {
        return torneo;
    }

    public void setTorneo(String torneo) {
        this.torneo = torneo;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public int getPuntosE1() {
        return puntosE1;
    }

    public void setPuntosE1(int puntosE1) {
        this.puntosE1 = puntosE1;
    }

    public int getPuntosE2() {
        return puntosE2;
    }

    public void setPuntosE2(int puntosE2) {
        this.puntosE2 = puntosE2;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", equipo1=" + equipo1 +
                ", equipo2=" + equipo2 +
                ", fecha=" + fecha +
                ", torneo='" + torneo + '\'' +
                ", jornada='" + jornada + '\'' +
                ", puntosE1=" + puntosE1 +
                ", puntosE2=" + puntosE2 +
                '}';
    }
}
