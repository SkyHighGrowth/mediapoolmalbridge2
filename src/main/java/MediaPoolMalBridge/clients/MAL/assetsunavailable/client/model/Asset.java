package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import com.google.gson.annotations.SerializedName;

public class Asset {

    @SerializedName( "asset_id" )
    private String assetId;

    @SerializedName( "property_id" )
    private String propertyId;

    private Urls urls;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}
