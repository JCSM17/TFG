package com.example.tfg.javi;

public class UserData {
    private long id;
    private String email; // Nuevo campo para el email
    private int selectedId; // Nuevo campo para el selectedId
    private float estatura;
    private int edad;
    private String sexo;
    private float peso;
    private String objetivo;
    private float calorias; // Nuevo campo para las calorías
    private String genero;
    // Constructor sin argumentos
    public UserData() {
    }

    // Modificar el constructor para aceptar y asignar el id y las calorías
    public UserData(long id, float estatura, int edad, String sexo, float peso, String objetivo, float calorias) {
        this.id = id;
        this.estatura = estatura;
        this.edad = edad;
        this.sexo = sexo;
        this.peso = peso;
        this.objetivo = objetivo;
        this.calorias = calorias; // Asignar el valor de las calorías
    }

    public UserData(long id, String email, int selectedId, float estatura, int edad, String genero, float peso, String objetivo, float calorias) {
        this.id = id;
        this.email = email; // Asignar el valor del email
        this.selectedId = selectedId; // Asignar el valor del selectedId
        this.estatura = estatura;
        this.edad = edad;
        this.sexo = genero;
        this.peso = peso;
        this.objetivo = objetivo;
        this.calorias = calorias; // Asignar el valor de las calorías
    }

    // Getter para el id
    public long getId() {
        return id;
    }

    // Setter para el id
    public void setId(long id) {
        this.id = id;
    }

    public String getGenero() {
        return this.genero;
    }

    // Getter para el email
    public String getEmail() {
        return email;
    }

    // Setter para el email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para el selectedId
    public int getSelectedId() {
        return selectedId;
    }

    // Setter para el selectedId
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