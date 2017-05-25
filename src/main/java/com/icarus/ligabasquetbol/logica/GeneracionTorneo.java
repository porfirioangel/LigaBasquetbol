package com.icarus.ligabasquetbol.logica;

import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GeneracionTorneo {
    private List<Equipo> equipos;
    private String nombreTorneo;
    private LocalDateTime fechaInicio;

    public GeneracionTorneo(String nombreTorneo, LocalDateTime fechaInicio,
                            List<Equipo> equipos) {
        this.nombreTorneo = nombreTorneo;
        this.fechaInicio = fechaInicio;
        this.equipos = equipos;
    }

    public List<Partido> GenerarTorneo() {
        List<Partido> partidos = new ArrayList<>();
        Partido p;
        int countDias = 0;
        if (equipos.size() % 2 == 1) {
            for (int i = 0; i < equipos.size() * 2; i += 2) {
                p = new Partido();
                p.setEquipo1(equipos.get(i % equipos.size()));
                p.setEquipo2(equipos.get((i + 1) % equipos.size()));
                p.setTorneo(nombreTorneo);
                p.setJornada("Clasificación");
                p.setFecha(fechaInicio.plusDays(countDias++));
                partidos.add(p);
            }
        } else {
            for (int i = 0; i < equipos.size(); i += 2) {
                p = new Partido();
                p.setEquipo1(equipos.get(i));
                p.setEquipo2(equipos.get(i + 1));
                p.setTorneo(nombreTorneo);
                p.setJornada("Clasificación");
                p.setFecha(fechaInicio.plusDays(countDias++));
                partidos.add(p);
            }
            for (int i = 0; i < equipos.size() / 2; i++) {
                p = new Partido();
                p.setEquipo1(equipos.get(i));
                p.setEquipo2(equipos.get(equipos.size() - 1 - i));
                p.setTorneo(nombreTorneo);
                p.setJornada("Clasificación");
                p.setFecha(fechaInicio.plusDays(countDias++));
                partidos.add(p);
            }
        }
        return partidos;
    }
}