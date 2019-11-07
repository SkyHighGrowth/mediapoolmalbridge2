package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetUnavailableAssetsResponse extends MALAbstractResponse {

    private List<Asset> assets;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
