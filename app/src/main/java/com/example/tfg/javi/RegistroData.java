package com.example.tfg.javi;

public class RegistroData {
    private int id;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private String suscripcion;
    private long fechaInicioSuscripcion;
    private int duracionSuscripcion;

    // Clase de datos para almacenar información de registro de usuario
    // Getters and setters para acceder y modificar los atributos privados de la clase

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(String suscripcion) {
        this.suscripcion = suscripcion;
    }

    public long getFechaInicioSuscripcion() {
        return fechaInicioSuscripcion;
    }

    public void setFechaInicioSuscripcion(long fechaInicioSuscripcion) {
        this.fechaInicioSuscripcion = fechaInicioSuscripcion;
    }

    public int getDuracionSuscripcion() {
        return duracionSuscripcion;
    }

    public void setDuracionSuscripcion(int duracionSuscripcion) {
        this.duracionSuscripcion = duracionSuscripcion;
    }
}
