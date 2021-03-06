package com.icarus.ligabasquetbol.persistencia.accesodatos;

import com.icarus.ligabasquetbol.persistencia.ConfigDb;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccesoJugador {
    public List<Jugador> obtenerTodos() {
        List<Jugador> jugadores = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            jugadores = sesion.selectList("allJugador");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de jugadores ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return jugadores;
    }

    public List<Jugador> obtenerDisponibles() {
        List<Jugador> jugadores = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            jugadores = sesion.selectList("getJugadoresDisponibles");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de jugadores ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return jugadores;
    }

    public List<Jugador> obtenerMiembros(Equipo equipo) {
        List<Jugador> jugadores = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            jugadores = sesion.selectList("getJugadoresDeEquipo", equipo);
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de jugadores ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return jugadores;
    }

    public boolean insertar(Jugador jugador) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            sesion.insert("insertJugador", jugador);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al insertar el jugador ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizar(Jugador jugador) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            ok = sesion.update("updateJugador", jugador) > 0;
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al actualizar el jugador ",
                    e + ">>" + e.getCause().getMessage(), Notification.Type
                            .ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public int quitarJugadoresDeEquipo(Equipo equipo) {
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        int actualizados = 0;
        try {
            actualizados = sesion.update("removeJugadorFromEquipo", equipo);
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al actualizar el jugador ",
                    e + ">>" + e.getCause().getMessage(), Notification.Type
                            .ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return actualizados;
    }

    public int eliminar(Jugador jugador) {
        int deletes = 0;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            deletes = sesion.delete("deleteJugador", jugador);
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al eliminar el jugador ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return deletes;
    }
}
