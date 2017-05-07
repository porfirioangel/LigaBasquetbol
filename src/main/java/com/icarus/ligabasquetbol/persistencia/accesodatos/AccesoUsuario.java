package com.icarus.ligabasquetbol.persistencia.accesodatos;

import com.icarus.ligabasquetbol.persistencia.ConfigDb;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AccesoUsuario {
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            usuarios = sesion.selectList("allUsuario");
        } catch (Exception e) {
            Notification.show("Error al recuperar la lista de usuarios ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return usuarios;
    }

    public boolean insertar(Usuario usuario) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            sesion.insert("insertUsuario", usuario);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al insertar el usuario ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizar(Usuario usuario) {
        boolean ok = false;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            sesion.insert("updateUsuario", usuario);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("Error al actualizar el usuario ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;
    }

    public int eliminar(Usuario usuario) {
        int deletes = 0;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            deletes = sesion.delete("deleteUsuario", usuario);
            sesion.commit();
        } catch (Exception e) {
            Notification.show("Error al eliminar el usuario ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return deletes;
    }

    public Usuario loguear(String email, String password) {
        Usuario usuario = null;
        SqlSession sesion = ConfigDb.getSqlMapper().openSession();
        try {
            Usuario auxUsuario = new Usuario();
            auxUsuario.setEmail(email);
            auxUsuario.setPassword(password);
            List<Usuario> usuarios = sesion.selectList("loginUsuario",
                    auxUsuario);
            usuario = usuarios.size() == 0 ? null : usuarios.get(0);
        } catch (Exception e) {
            Notification.show("Error al realizar el login ",
                    e.getCause().getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return usuario;
    }
}
