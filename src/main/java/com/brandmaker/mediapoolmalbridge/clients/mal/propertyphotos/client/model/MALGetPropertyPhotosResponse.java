package com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client.MALGetPropertyPhotosClient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response of {@link MALGetPropertyPhotosClient}
 */
public class MALGetPropertyPhotosResponse extends MALAbstractResponse {

    @SerializedName("asset_ids")
    private List<String> assetIds;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }
}
