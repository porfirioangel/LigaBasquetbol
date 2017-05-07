package com.icarus.ligabasquetbol.persistencia.modelos;

public class Usuario {
    private int id;
    private String email;
    private String password;
    private String tipoUsuario;
    private String claveVerificacion;

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getClaveVerificacion() {
        return claveVerificacion;
    }

    public void setClaveVerificacion(String claveVerificacion) {
        this.claveVerificacion = claveVerificacion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", claveVerificacion='" + claveVerificacion + '\'' +
                '}';
    }
}
