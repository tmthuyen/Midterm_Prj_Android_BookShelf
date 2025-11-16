package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class AccessInfo {
    @SerializedName("country")
    private String country;

    @SerializedName("viewability")
    private String viewability;

    @SerializedName("embeddable")
    private Boolean embeddable;

    @SerializedName("publicDomain")
    private Boolean publicDomain;

    @SerializedName("textToSpeechPermission")
    private String textToSpeechPermission;

    @SerializedName("epub")
    private Epub epub;

    @SerializedName("pdf")
    private Pdf pdf;

    @SerializedName("accessViewStatus")
    private String accessViewStatus;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    public Boolean getPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(Boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }
}
