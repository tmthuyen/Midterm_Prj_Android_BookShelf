package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("amount")
    private Double amount;

    @SerializedName("currencyCode")
    private String currencyCode;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
