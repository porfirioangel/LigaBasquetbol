package com.icarus.ligabasquetbol.persistencia.accesodatos;

import com.icarus.ligabasquetbol.persistencia.ConfigDb;
import com.icarus.ligabasquetbol.persistencia.modelos.Partido;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccesoPartido {
    public List<Partido> obtenerTodos() {
        List<Partido> partidos = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            partidos = sesion.selectList("allPartido");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de partidos ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return partidos;
    }

    public boolean insertar(List<Partido> partidos) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            for (Partido partido : partidos) {
                sesion.insert("insertPartido", partido);
            }
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al insertar los partidos",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizarMarcador(Partido partido) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            ok = sesion.update("updateMarcadorPartido", partido) > 0;
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al actualizar el partido ",
                    e + ">>" + e.getCause().getMessage(), Notification.Type
                            .ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public int vaciarPartidos() {
        int deletes = 0;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            deletes = sesion.delete("deleteAllPartido");
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al vaciar los partidos ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return deletes;
    }
}
