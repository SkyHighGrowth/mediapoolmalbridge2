package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MAL server get assets response for the client {@link com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.MALGetNewBrandAssetsClient}
 */
public class MALGetNewAssetsResponse {

    @SerializedName("data")
    public List<MALGetData> data;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("meta")
    public MALMeta meta;

    public List<MALGetData> getData() {
        return data;
    }

    public void setData(List<MALGetData> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public MALMeta getMeta() {
        return meta;
    }

    public void setMeta(MALMeta meta) {
        this.meta = meta;
    }
}
