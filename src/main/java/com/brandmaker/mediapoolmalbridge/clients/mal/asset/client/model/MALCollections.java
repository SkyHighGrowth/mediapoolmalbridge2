package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO for fetching asset collections from api/syncs
 */
public class MALCollections {

    @SerializedName("data")
    public List<Object> data;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
