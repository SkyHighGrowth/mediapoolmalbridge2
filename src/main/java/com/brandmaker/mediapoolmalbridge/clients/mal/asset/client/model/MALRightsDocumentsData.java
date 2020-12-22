package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset document rights data from api/syncs
 */
public class MALRightsDocumentsData {
    @SerializedName("rights_document_id")
    public String rightsDocumentId;
    @SerializedName("document")
    public String document;
    @SerializedName("date_uploaded")
    public String dateUploaded;
    @SerializedName("url")
    public String url;
    @SerializedName("name")
    public String name;
    @SerializedName("global")
    public Boolean global;

    public String getRightsDocumentId() {
        return rightsDocumentId;
    }

    public void setRightsDocumentId(String rightsDocumentId) {
        this.rightsDocumentId = rightsDocumentId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }
}
