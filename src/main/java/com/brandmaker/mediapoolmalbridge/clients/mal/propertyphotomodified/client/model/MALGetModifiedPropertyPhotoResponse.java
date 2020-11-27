package com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotomodified.client.model;

import com.brandmaker.mediapoolmalbridge.clients.mal.singleresponse.MALAbstractResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;

import java.util.List;

/**
 * Response of {@link MALGetModifiedPropertyPhotoClient}
 */
public class MALGetModifiedPropertyPhotoResponse extends MALAbstractResponse {

    /**
     * List of {@link MALModifiedPropertyPhotoAsset}
     */
    private List<MALModifiedPropertyPhotoAsset> assets;

    public List<MALModifiedPropertyPhotoAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<MALModifiedPropertyPhotoAsset> assets) {
        this.assets = assets;
    }
}
