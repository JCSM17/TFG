package com.example.tfg.jesus;

public class ImageItem {
    private int imageResId;
    private String description;

    public ImageItem(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
