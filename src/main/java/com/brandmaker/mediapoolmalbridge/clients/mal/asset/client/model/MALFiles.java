package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO for fetching asset files from api/syncs
 */
public class MALFiles {

    @SerializedName("data")
    public List<MALFilesData> data;

    public List<MALFilesData> getData() {
        return data;
    }

    public void setData(List<MALFilesData> data) {
        this.data = data;
    }
}
