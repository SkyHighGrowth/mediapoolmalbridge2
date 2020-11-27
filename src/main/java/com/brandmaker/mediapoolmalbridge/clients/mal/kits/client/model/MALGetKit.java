package com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetKitsResponse}
 */
public class MALGetKit {

    @SerializedName("kit_id")
    private String kitId;

    private String name;

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
