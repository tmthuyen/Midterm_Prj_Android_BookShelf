package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class SaleInfo {
    @SerializedName("country")
    private String country;

    @SerializedName("saleability")
    private String saleability;

    @SerializedName("isEbook")
    private Boolean isEbook;

    @SerializedName("listPrice")
    private Price listPrice;

    @SerializedName("retailPrice")
    private Price retailPrice;

    @SerializedName("buyLink")
    private String buyLink;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public Boolean getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(Boolean isEbook) {
        this.isEbook = isEbook;
    }

    public Price getListPrice() {
        return listPrice;
    }

    public void setListPrice(Price listPrice) {
        this.listPrice = listPrice;
    }

    public Price getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Price retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }
}
