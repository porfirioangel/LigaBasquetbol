package com.icarus.ligabasquetbol.persistencia.modelos;

public class Equipo {
    private int id;
    private String nombre;
    private String urlLogo;
    private Entrenador entrenador;

    public Equipo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        if (id != equipo.id) return false;
        if (nombre != null ? !nombre.equals(equipo.nombre)
                : equipo.nombre != null)
            return false;
        if (urlLogo != null ? !urlLogo.equals(equipo.urlLogo)
                : equipo.urlLogo != null)
            return false;
        return entrenador != null ? entrenador.equals(equipo.entrenador)
                : equipo.entrenador == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (urlLogo != null ? urlLogo.hashCode() : 0);
        result = 31 * result + (entrenador != null ? entrenador.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NombreEquipo:" + nombre;
    }
}
