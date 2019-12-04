package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;

import java.util.List;

/**
 * Class that represents response of {@link MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient}
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
