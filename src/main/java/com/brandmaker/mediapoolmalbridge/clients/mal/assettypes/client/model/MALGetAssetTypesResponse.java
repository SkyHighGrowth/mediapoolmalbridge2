package com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MALGetAssetTypesResponse extends MALAbstractResponse {

    @SerializedName("asset_types")
    private List<AssetType> assetTypes;

    public List<AssetType> getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(List<AssetType> assetTypes) {
        this.assetTypes = assetTypes;
    }
}
