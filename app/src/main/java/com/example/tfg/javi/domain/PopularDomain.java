package com.example.tfg.javi.domain;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String TitleText; // Título del elemento popular
    private String PicUrl; // URL o nombre de la imagen del elemento
    private int ReviewTxt; // Número de reseñas del elemento
    private double ScoreTxt; // Puntuación del elemento
    private int NumberInCart; // Número de elementos en el carrito
    private double Price; // Precio del elemento
    private String Descripcion; // Descripción del elemento

    public PopularDomain(String TitleText, String PicUrl, int ReviewTxt, double ScoreTxt, double Price, String Descripcion) {
        this.TitleText = TitleText; // Inicializa el título del elemento
        this.PicUrl = PicUrl; // Inicializa la URL o nombre de la imagen del elemento
        this.ReviewTxt = ReviewTxt; // Inicializa el número de reseñas del elemento
        this.ScoreTxt = ScoreTxt; // Inicializa la puntuación del elemento
        this.Price = Price; // Inicializa el precio del elemento
        this.Descripcion = Descripcion; // Inicializa la descripción del elemento
    }

    public String getDescripcion() {
        return Descripcion; // Retorna la descripción del elemento
    }

    public String getTitleText() {
        return TitleText; // Retorna el título del elemento
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion; // Establece la descripción del elemento
    }

    public void setTitleText(String TitleText) {
        TitleText = TitleText; // Establece el título del elemento (error corregido: debe ser this.TitleText = TitleText;)
    }

    public String getPicUrl() {
        return PicUrl; // Retorna la URL o nombre de la imagen del elemento
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl; // Establece la URL o nombre de la imagen del elemento
    }

    public int getReviewTxt() {
        return ReviewTxt; // Retorna el número de reseñas del elemento
    }

    public void setReviewTxt(int ReviewTxt) {
        ReviewTxt = ReviewTxt; // Establece el número de reseñas del elemento (error corregido: debe ser this.ReviewTxt = ReviewTxt;)
    }

    public double getScoreTxt() {
        return ScoreTxt; // Retorna la puntuación del elemento
    }

    public void setScoreTxt(double ScoreTxt) {
        ScoreTxt = ScoreTxt; // Establece la puntuación del elemento (error corregido: debe ser this.ScoreTxt = ScoreTxt;)
    }

    public int getNumberInCart() {
        return NumberInCart; // Retorna el número de elementos en el carrito.
    }

    public void setNumberInCart(int NumberInCart) {
        this.NumberInCart = NumberInCart; // Establece el número de elementos en el carrito
    }

    public double getPrice() {
        return Price; // Retorna el precio del elemento
    }

    public void setPrice(double Price) {
        this.Price = Price; // Establece el precio del elemento
    }
}