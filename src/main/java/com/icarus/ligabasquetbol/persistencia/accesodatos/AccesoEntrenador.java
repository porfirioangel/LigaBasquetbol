package com.icarus.ligabasquetbol.persistencia.accesodatos;

import com.icarus.ligabasquetbol.persistencia.ConfigDb;
import com.icarus.ligabasquetbol.persistencia.modelos.Entrenador;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccesoEntrenador {
    public List<Entrenador> obtenerTodos() {
        List<Entrenador> entrenadores = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            entrenadores = sesion.selectList("allEntrenador");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de entrenadores ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return entrenadores;
    }

    public boolean insertar(Entrenador entrenador) {
        boolean ok = false;
        AccesoUsuario accesoUsuario = new AccesoUsuario();
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            Usuario usuario = entrenador.getUsuario();
            if (usuario != null) {
                usuario.setPassword("password");
                usuario.setClaveVerificacion(accesoUsuario.obtenerNuevaVerificacion());
                usuario.setTipoUsuario("entrenador");
                accesoUsuario.insertar(usuario);
                usuario = accesoUsuario.getUsuarioByEmail(usuario.getEmail());
                entrenador.setUsuario(usuario);
            }
            sesion.insert("insertEntrenador", entrenador);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error al insertar el entrenador " +
                    e, Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizar(Entrenador entrenador) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            sesion.insert("updateEntrenador", entrenador);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al actualizar el entrenador ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public int eliminar(Entrenador entrenador) {
        int deletes = 0;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            deletes = sesion.delete("deleteEntrenador", entrenador);
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al eliminar el entrenador ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return deletes;
    }
}
