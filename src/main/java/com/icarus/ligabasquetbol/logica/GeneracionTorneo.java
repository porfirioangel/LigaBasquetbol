package com.icarus.ligabasquetbol.logica;

import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;

import java.time.LocalDateTime;
import java.util.*;

public class GeneracionTorneo {
    public static List<Partido> generarClasificatorio(String nombreTorneo,
                                                      LocalDateTime fechaInicio,
                                                      List<Equipo> equipos) {
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

    public static List<Partido> generarSiguienteFase(List<Partido> anteriores) {
        System.out.println(">> Partidos anteriores: " + anteriores);
        int numEquipos = ordenarEquipos(anteriores).size();
        System.out.println(">> NumEquipos: " + numEquipos);
        List<Partido> nuevosPartidos = new ArrayList<>();
        int countDias = 1;
        LocalDateTime fechaPartido = anteriores.get(anteriores.size() - 1)
                .getFecha();
        String torneo = anteriores.get(anteriores.size() - 1).getTorneo();
        if (numEquipos > 8) {
            List<EquipoPuntos> equiposOrdenados = ordenarEquipos(anteriores);
            List<Equipo> equiposPasados = new ArrayList<>();
            int countPasados = 1;
            for (EquipoPuntos ep : equiposOrdenados) {
                equiposPasados.add(ep.getEquipo());
                if (countPasados++ == 8)
                    break;
            }
            Partido partido = new Partido();
            partido.setJornada("Cuartos de final");
            partido.setEquipo1(equiposPasados.get(0));
            partido.setEquipo2(equiposPasados.get(7));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
            partido = new Partido();
            partido.setJornada("Cuartos de final");
            partido.setEquipo1(equiposPasados.get(1));
            partido.setEquipo2(equiposPasados.get(6));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
            partido = new Partido();
            partido.setJornada("Cuartos de final");
            partido.setEquipo1(equiposPasados.get(2));
            partido.setEquipo2(equiposPasados.get(5));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
            partido = new Partido();
            partido.setJornada("Cuartos de final");
            partido.setEquipo1(equiposPasados.get(3));
            partido.setEquipo2(equiposPasados.get(4));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
        } else if (numEquipos > 4) {
            List<EquipoPuntos> equiposOrdenados = ordenarEquipos(anteriores);
            List<Equipo> equiposPasados = new ArrayList<>();
            int countPasados = 1;
            for (EquipoPuntos ep : equiposOrdenados) {
                equiposPasados.add(ep.getEquipo());
                if (countPasados++ == 4)
                    break;
            }
            Partido partido = new Partido();
            partido.setJornada("Semifinales");
            partido.setEquipo1(equiposPasados.get(0));
            partido.setEquipo2(equiposPasados.get(3));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
            partido = new Partido();
            partido.setJornada("Semifinales");
            partido.setEquipo1(equiposPasados.get(1));
            partido.setEquipo2(equiposPasados.get(2));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
        } else if (numEquipos > 2) {
            List<EquipoPuntos> equiposOrdenados = ordenarEquipos(anteriores);
            List<Equipo> equiposPasados = new ArrayList<>();
            int countPasados = 1;
            for (EquipoPuntos ep : equiposOrdenados) {
                equiposPasados.add(ep.getEquipo());
                if (countPasados++ == 2)
                    break;
            }
            Partido partido = new Partido();
            partido.setJornada("Final");
            partido.setEquipo1(equiposPasados.get(0));
            partido.setEquipo2(equiposPasados.get(1));
            partido.setFecha(fechaPartido.plusDays(countDias++));
            partido.setTorneo(torneo);
            nuevosPartidos.add(partido);
        } else {
            return null;
        }
        System.out.println(">> NuevosPartidos: " + nuevosPartidos);
        return nuevosPartidos;
    }

    private static List<EquipoPuntos> ordenarEquipos(List<Partido> partidos) {
        Map<Equipo, EquipoPuntos> mapaEquipos = new HashMap<>();
        Equipo equipo1;
        Equipo equipo2;
        int puntosE1;
        int puntosE2;
        for (Partido partido : partidos) {
            equipo1 = partido.getEquipo1();
            equipo2 = partido.getEquipo2();
            if (partido.getPuntosE1() < partido.getPuntosE2()) {
                puntosE1 = 1;
                puntosE2 = 3;
            } else if (partido.getPuntosE1() > partido.getPuntosE2()) {
                puntosE1 = 3;
                puntosE2 = 1;
            } else {
                puntosE1 = 2;
                puntosE2 = 2;
            }
            if (!mapaEquipos.containsKey(equipo1)) {
                System.out.println(">> added equipo: " + equipo1);
                mapaEquipos.put(equipo1, new EquipoPuntos(equipo1));
            }
            mapaEquipos.get(equipo1).puntos += puntosE1;
            if (!mapaEquipos.containsKey(equipo2)) {
                System.out.println(">> added equipo: " + equipo2);
                mapaEquipos.put(equipo2, new EquipoPuntos(equipo2));
            }
            mapaEquipos.get(equipo2).puntos += puntosE2;
        }
        List<EquipoPuntos> equiposOrdenados = new ArrayList<>();
        for (Equipo ep : mapaEquipos.keySet()) {
            equiposOrdenados.add(mapaEquipos.get(ep));
        }
        Collections.sort(equiposOrdenados);
        return equiposOrdenados;
    }

    private static class EquipoPuntos implements Comparable<EquipoPuntos> {
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
}