package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Response of {@link MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient}
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
