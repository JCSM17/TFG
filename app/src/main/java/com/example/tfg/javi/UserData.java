package com.example.tfg.javi;

public class UserData {
    private int id;
    private String email;
    private int selectedId;
    private float estatura;
    private int edad;
    private String sexo;
    private float peso;
    private String objetivo;
    private float calorias; // Nuevo campo para las calorías

    // Constructor sin argumentos
    public UserData() {
    }

    // Modificar el constructor para aceptar y asignar el id y las calorías
    public UserData(int id, String email, int selectedId, float estatura, int edad, String sexo, float peso, String objetivo, float calorias) {
        this.id = id;
        this.email = email;
        this.selectedId = selectedId;
        this.estatura = estatura;
        this.edad = edad;
        this.sexo = sexo;
        this.peso = peso;
        this.objetivo = objetivo;
        this.calorias = calorias; // Asignar el valor de las calorías
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

    public String getSexo() {
        return sexo;
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
        return calorias; // Getter para las calorías
    }

    public void setCalorias(float calorias) {
        this.calorias = calorias; // Setter para las calorías
    }
}
