package com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client.MALGetUnavailableAssetsClient;

import java.util.List;

/**
 * Class that represents response of {@link MALGetUnavailableAssetsClient}
 */
public class MALGetUnavailableAssetsResponse extends MALAbstractResponse {

    /**
     * List of MALGetUnavailableAsset objects
     */
    private List<MALGetUnavailableAsset> assets;

    public List<MALGetUnavailableAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<MALGetUnavailableAsset> assets) {
        this.assets = assets;
    }
}
