package com.example.tfg.javi;

public class UserData {
    private long id;
    private String email; // Campo para almacenar el correo electrónico del usuario
    private float estatura; // Almacena la estatura del usuario
    private int edad; // Almacena la edad del usuario
    private String sexo; // Almacena el sexo del usuario (probablemente 'M' o 'F')
    private float peso; // Almacena el peso del usuario
    private String objetivo; // Almacena el objetivo del usuario (por ejemplo, "perder peso", "ganar músculo")
    private float calorias; // Almacena la cantidad de calorías recomendadas para el usuario

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
        return email; // Devuelve el correo electrónico almacenado
    }

    public void setEmail(String email) {
        this.email = email; // Establece el correo electrónico del usuario
    }

    public float getEstatura() {
        return estatura; // Devuelve la estatura del usuario
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura; // Establece la estatura del usuario
    }

    public int getEdad() {
        return edad; // Devuelve la edad del usuario
    }

    public void setEdad(int edad) {
        this.edad = edad; // Establece la edad del usuario
    }

    public String getSexo() {
        return this.sexo; // Devuelve el sexo del usuario
    }

    public void setSexo(String sexo) {
        this.sexo = sexo; // Establece el sexo del usuario
    }

    public float getPeso() {
        return peso; // Devuelve el peso del usuario
    }

    public void setPeso(float peso) {
        this.peso = peso; // Establece el peso del usuario
    }

    public String getObjetivo() {
        return objetivo; // Devuelve el objetivo del usuario
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo; // Establece el objetivo del usuario
    }

    public float getCalorias() {
        return calorias; // Devuelve las calorías recomendadas para el usuario
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias; // Establece las calorías recomendadas para el usuario
    }
}