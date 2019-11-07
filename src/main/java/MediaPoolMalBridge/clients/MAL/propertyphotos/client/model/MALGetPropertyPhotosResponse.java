package MediaPoolMalBridge.clients.MAL.propertyphotos.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MALGetPropertyPhotosResponse extends MALAbstractResponse {

    @SerializedName( "asset_ids" )
    private List<String> assetIds;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }
}
