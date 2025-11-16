package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeResponse {
    @SerializedName("kind")
    private String kind;

    @SerializedName("totalItems")
    private int totalItems;

    @SerializedName("items")
    private List<VolumeItem> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<VolumeItem> getItems() {
        return items;
    }

    public void setItems(List<VolumeItem> items) {
        this.items = items;
    }
}
