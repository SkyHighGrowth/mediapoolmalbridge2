package MediaPoolMalBridge.clients.MAL.assettypes.client.model;

import com.google.gson.annotations.SerializedName;

public class AssetType {

    @SerializedName( "asset_type_id" )
    private String assetTypeId;

    private String name;

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
