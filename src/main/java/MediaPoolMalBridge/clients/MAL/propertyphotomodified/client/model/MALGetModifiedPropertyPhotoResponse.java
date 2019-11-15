package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetModifiedPropertyPhotoResponse extends MALAbstractResponse {

    private List<MALModifiedPropertyPhotoAsset> assets;

    public List<MALModifiedPropertyPhotoAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<MALModifiedPropertyPhotoAsset> assets) {
        this.assets = assets;
    }
}
