package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for fetching asset property from api/syncs
 */
public class MALProperty {
    @SerializedName("data")
    public MALPropertyData data;

    public MALPropertyData getData() {
        return data;
    }

    public void setData(MALPropertyData data) {
        this.data = data;
    }
}
