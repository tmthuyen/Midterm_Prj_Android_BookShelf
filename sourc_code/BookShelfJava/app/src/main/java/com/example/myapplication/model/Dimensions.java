package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Dimensions {
    @SerializedName("height")
    private String height;

    @SerializedName("width")
    private String width;

    @SerializedName("thickness")
    private String thickness;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }
}
