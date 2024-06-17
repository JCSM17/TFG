package com.example.tfg.javi;

public class UserData {
    private long id;
    private String email;
    private float estatura;
    private int edad;
    private String sexo;
    private float peso;
    private String objetivo;
    private float calorias;

    public UserData(long id, float estatura, int edad, String sexo, float peso, String objetivo, float calorias) {
        this.id = id;
        this.estatura = estatura;
        this.edad = edad;
        this.sexo = sexo;
        this.peso = peso;
        this.objetivo = objetivo;
        this.calorias = calorias;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public float getCalorias() {
        return calorias;
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }
}