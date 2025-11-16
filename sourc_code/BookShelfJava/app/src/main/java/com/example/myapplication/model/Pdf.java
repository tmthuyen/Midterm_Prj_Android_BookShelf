package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Pdf {
    @SerializedName("isAvailable")
    private Boolean isAvailable;

    @SerializedName("downloadLink")
    private String downloadLink;

    @SerializedName("acsTokenLink")
    private String acsTokenLink;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getAcsTokenLink() {
        return acsTokenLink;
    }

    public void setAcsTokenLink(String acsTokenLink) {
        this.acsTokenLink = acsTokenLink;
    }
}
