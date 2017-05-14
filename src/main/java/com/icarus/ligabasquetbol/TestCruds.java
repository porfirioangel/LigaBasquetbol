package com.icarus.ligabasquetbol;

import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEquipo;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoJugador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.icarus.ligabasquetbol.persistencia.modelos.Entrenador;
import com.icarus.ligabasquetbol.persistencia.modelos.Equipo;
import com.icarus.ligabasquetbol.persistencia.modelos.Jugador;
import com.icarus.ligabasquetbol.persistencia.modelos.Usuario;
import com.icarus.ligabasquetbol.utils.GeneradorClaves;
import com.icarus.ligabasquetbol.utils.Sha1;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class TestCruds {
    public static void test() {
        testUsuarioCrud();
        testEquipoCrud();
        testJugadorCrud();
        testEntrenadorCrud();
    }

    public static void testUsuarioCrud() {
        AccesoUsuario acceso = new AccesoUsuario();
        List<Usuario> usuarios = acceso.obtenerTodos();
        System.out.println(">> Usuarios: " + usuarios.toString());
        Usuario usuario = null;
        if (usuarios.size() > 0)
            usuario = usuarios.get(usuarios.size() - 1);
        else
            usuario = new Usuario();
        usuario.setEmail(usuario.getEmail() + ".");
        usuario.setPassword(Sha1.toSha1("password"));
        usuario.setTipoUsuario("administrador");
        usuario.setClaveVerificacion(GeneradorClaves.generarClave());
        boolean exito = acceso.insertar(usuario);
        System.out.println(">> Insert usuario: " + exito);
        usuarios = acceso.obtenerTodos();
        usuario = usuarios.get(usuarios.size() - 1);
        usuario.setPassword(Sha1.toSha1(usuario.getPassword()));
        exito = acceso.actualizar(usuario);
        System.out.println(">> Update usuario: " + exito);
        exito = acceso.eliminar(usuario) > 0;
        System.out.println(">> Delete usuario: " + exito);
    }

    public static void testEquipoCrud() {
        AccesoEquipo acceso = new AccesoEquipo();
        List<Equipo> equipos = acceso.obtenerTodos();
        System.out.println(">> Equipos: " + equipos.toString());
        Equipo equipo = null;
        if (equipos.size() > 0)
            equipo = equipos.get(equipos.size() - 1);
        else
            equipo = new Equipo();
        equipo.setNombre(equipo.getNombre() + ".");
        equipo.setUrlLogo(equipo.getUrlLogo() + "'");
        boolean exito = acceso.insertar(equipo);
        System.out.println(">> Insert equipo: " + exito);
        equipos = acceso.obtenerTodos();
        equipo = equipos.get(equipos.size() - 1);
        equipo.setNombre(equipo.getNombre() + "¡");
        equipo.setUrlLogo(equipo.getUrlLogo() + ")");
        exito = acceso.actualizar(equipo);
        System.out.println(">> Update equipo: " + exito);
        exito = acceso.eliminar(equipo) > 0;
        System.out.println(">> Delete equipo: " + exito);
    }

    public static void testJugadorCrud() {
        AccesoJugador acceso = new AccesoJugador();
        List<Jugador> jugadores = acceso.obtenerTodos();
        System.out.println(">> Jugadores: " + jugadores.toString());
        Jugador jugador = null;
        if (jugadores.size() > 0)
            jugador = jugadores.get(jugadores.size() - 1);
        else
            jugador = new Jugador();
        jugador.setNombre("PorfirioTest");
        jugador.setApPaterno("DíazTest");
        jugador.setApMaterno("SánchezTest");
        jugador.setTelefono(jugador.getTelefono() + "9");
        jugador.setFechaNacimiento(Date.valueOf(LocalDate.now()));
        jugador.setUrlFoto(jugador.getTelefono() + ".jpg");
        jugador.setEquipo(jugador.getEquipo());
        boolean exito = acceso.insertar(jugador);
        System.out.println(">> Insert jugador: " + exito);
        jugadores = acceso.obtenerTodos();
        jugador = jugadores.get(jugadores.size() - 1);
        jugador.setTelefono(jugador.getTelefono() + "2");
        exito = acceso.actualizar(jugador);
        System.out.println(">> Update jugador: " + exito);
        exito = acceso.eliminar(jugador) > 0;
        System.out.println(">> Delete jugador: " + exito);
    }

    public static void testEntrenadorCrud() {
        AccesoEntrenador acceso = new AccesoEntrenador();
        List<Entrenador> entrenadores = acceso.obtenerTodos();
        System.out.println(">> Entrenadores: " + entrenadores.toString());
        Entrenador entrenador = null;
        if (entrenadores.size() > 0)
            entrenador = entrenadores.get(entrenadores.size() - 1);
        else
            entrenador = new Entrenador();
        entrenador.setNombre("PorfirioTest");
        entrenador.setApPaterno("DíazTest");
        entrenador.setApMaterno("SánchezTest");
        entrenador.setTelefono(entrenador.getTelefono() + "9");
        entrenador.setFechaNacimiento(Date.valueOf(LocalDate.now()));
        entrenador.setUrlFoto(entrenador.getTelefono() + ".jpg");
        entrenador.setEquipo(entrenador.getEquipo());
        entrenador.setUsuario(null);
        boolean exito = acceso.insertar(entrenador);
        System.out.println(">> Insert entrenador: " + exito);
        entrenadores = acceso.obtenerTodos();
        entrenador = entrenadores.get(entrenadores.size() - 1);
        entrenador.setTelefono(entrenador.getTelefono() + "2");
        exito = acceso.actualizar(entrenador);
        System.out.println(">> Update entrenador: " + exito);
        exito = acceso.eliminar(entrenador) > 0;
        System.out.println(">> Delete entrenador: " + exito);
    }
}
