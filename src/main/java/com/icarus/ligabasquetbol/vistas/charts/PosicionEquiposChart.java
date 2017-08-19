package com.icarus.ligabasquetbol.vistas.charts;

import com.icarus.ligabasquetbol.logica.EquipoPuntos;
import com.icarus.ligabasquetbol.logica.GeneracionTorneo;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.List;

public class PosicionEquiposChart {
    public static Chart create(List<Partido> partidos) {
        List<EquipoPuntos> equipoPuntos = GeneracionTorneo
                .ordenarEquipos(partidos);
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Puntuaciones del Torneo");
        XAxis x = new XAxis();
        x.setCategories(getNombresEquipo(equipoPuntos));
        conf.addxAxis(x);
        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Puntos");
        conf.addyAxis(y);
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("'Equipo: ' + this.x +', '+ this.y +' puntos'");
        conf.setTooltip(tooltip);
        GeneradorSeries generador = new GeneradorSeries(partidos, equipoPuntos);
        conf.addSeries(generador.serieCalificados);
        conf.addSeries(generador.serieDescalificados);
        new DataSeriesItem().setDataLabels(new DataLabels(true));
        chart.drawChart(conf);
        return chart;
    }

    private static String[] getNombresEquipo(List<EquipoPuntos> equiposPuntos) {
        String[] nombresEquipos = new String[equiposPuntos.size()];
        for (int i = 0; i < nombresEquipos.length; i++) {
            nombresEquipos[i] = equiposPuntos.get(i).getEquipo().getNombre();
        }
        return nombresEquipos;
    }

    private static class GeneradorSeries {
        private DataSeries serieCalificados;
        private DataSeries serieDescalificados;

        public GeneradorSeries(List<Partido> partidos,
                               List<EquipoPuntos> equipoPuntos) {
            List<EquipoPuntos> calificados = new ArrayList<>();
            List<EquipoPuntos> descalificados = new ArrayList<>();
            String ultimaJornada = partidos.get(partidos.size() - 1)
                    .getJornada();
            for (EquipoPuntos ep : equipoPuntos) {
                boolean calificado = false;
                for (Partido p : partidos) {
                    if (p.getJornada().equals(ultimaJornada)) {
                        if (p.getEquipo1().equals(ep.getEquipo())
                                || p.getEquipo2().equals(ep.getEquipo())) {
                            calificado = true;
                            calificados.add(ep);
                            break;
                        }
                    }
                }
                if (!calificado) {
                    descalificados.add(ep);
                }
            }
            String[] nombresCalificados = new String[calificados.size()];
            Number[] puntosCalificados = new Number[calificados.size()];
            int i = 0;
            for (EquipoPuntos ep : calificados) {
                nombresCalificados[i] = ep.getEquipo().getNombre();
                puntosCalificados[i++] = ep.getPuntos();
            }
            serieCalificados = new DataSeries(nombresCalificados,
                    puntosCalificados);
            serieCalificados.setName("Calificados");
            i = 0;
            String[] nombresDescalificados = new String[descalificados.size()];
            Number[] puntosDescalificados = new Number[descalificados.size()];
            for (EquipoPuntos ep : descalificados) {
                nombresDescalificados[i] = ep.getEquipo().getNombre();
                puntosDescalificados[i++] = ep.getPuntos();
            }
            serieDescalificados = new DataSeries(nombresDescalificados,
                    puntosDescalificados);
            serieDescalificados.setName("Descalificados");
        }
    }
}