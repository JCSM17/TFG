package com.example.tfg.javi;

public class UserData {
    private int id;
    private String email;
    private int selectedId;
    private float estatura;
    private int edad;
    private String genero;
    private float peso;
    private String objetivo;

    // Modificar el constructor para aceptar y asignar el id
    public UserData(int id, String email, int selectedId, float estatura, int edad, String genero, float peso, String objetivo) {
        this.id = id;
        this.email = email;
        this.selectedId = selectedId;
        this.estatura = estatura;
        this.edad = edad;
        this.genero = genero;
        this.peso = peso;
        this.objetivo = objetivo;
    }

    // Getter para el id
    public int getId() {
        return id;
    }

    // Setter para el id
    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}