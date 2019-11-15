package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

public class MALGetUnavailableAssetsResponse extends MALAbstractResponse {

    private List<MALGetUnavailableAsset> assets;

    public List<MALGetUnavailableAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<MALGetUnavailableAsset> assets) {
        this.assets = assets;
    }
}
