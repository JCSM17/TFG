package com.example.tfg.javi.domain;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String TitleText;
    private String PicUrl;
    private int ReviewTxt;
    private double ScoreTxt;
    private int NumberInCart;
    private double Price;
    private String Descripcion;

    public PopularDomain(String TitleText, String PicUrl, int ReviewTxt, double ScoreTxt, double Price, String Descripcion) {
        this.TitleText = TitleText;
        this.PicUrl = PicUrl;
        this.ReviewTxt = ReviewTxt;
        this.ScoreTxt = ScoreTxt;
        this.Price = Price;
        this.Descripcion = Descripcion;
    }

    public String getDescripcion(){
        return Descripcion;
    }
    public String getTitleText() {
        return TitleText;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setTitleText(String TitleText) {
        TitleText = TitleText;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public int getReviewTxt() {
        return ReviewTxt;
    }

    public void setReviewTxt(int ReviewTxt) {
        ReviewTxt = ReviewTxt;
    }

    public double getScoreTxt() {
        return ScoreTxt;
    }

    public void setScoreTxt(double ScoreTxt) {
        ScoreTxt = ScoreTxt;
    }

    public int getNumberInCart() {
        return NumberInCart;
    }

    public void setNumberInCart(int NumberInCart) {
        this.NumberInCart = NumberInCart;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
}