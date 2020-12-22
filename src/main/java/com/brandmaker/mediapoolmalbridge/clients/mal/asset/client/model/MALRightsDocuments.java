package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO for fetching asset document rights from api/syncs
 */
public class MALRightsDocuments {

    @SerializedName("data")
    public List<MALRightsDocumentsData> data;

    public List<MALRightsDocumentsData> getData() {
        return data;
    }

    public void setData(List<MALRightsDocumentsData> data) {
        this.data = data;
    }
}
