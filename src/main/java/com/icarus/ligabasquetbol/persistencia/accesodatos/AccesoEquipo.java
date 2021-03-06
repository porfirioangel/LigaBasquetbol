package com.icarus.ligabasquetbol.persistencia.accesodatos;

import com.icarus.ligabasquetbol.persistencia.ConfigDb;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccesoEquipo {
    public List<Equipo> obtenerTodos() {
        List<Equipo> equipos = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            equipos = sesion.selectList("allEquipo");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de equipos ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return equipos;
    }

    public Equipo obtenerEquipoPorNombre(String nombre) {
        List<Equipo> equipos = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            Equipo equipo = new Equipo();
            equipo.setNombre(nombre);
            equipos = sesion.selectList("getEquipoByNombre", equipo);
            return equipos.size() > 0 ? equipos.get(0) : null;
        } catch (Exception e) {
            Notification.show("Error al recuperar el equipo ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return null;
    }

    public boolean insertar(Equipo equipo) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            sesion.insert("insertEquipo", equipo);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al insertar el equipo ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizar(Equipo equipo) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            ok = sesion.update("updateEquipo", equipo) > 0;
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al actualizar el equipo ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public int eliminar(Equipo equipo) {
        int deletes = 0;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            deletes = sesion.delete("deleteEquipo", equipo);
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al eliminar el equipo ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return deletes;
    }
}
