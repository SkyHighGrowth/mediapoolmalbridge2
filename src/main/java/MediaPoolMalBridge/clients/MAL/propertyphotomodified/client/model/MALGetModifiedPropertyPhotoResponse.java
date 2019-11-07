package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetModifiedPropertyPhotoResponse extends MALAbstractResponse {

    private List<Asset> assets;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
