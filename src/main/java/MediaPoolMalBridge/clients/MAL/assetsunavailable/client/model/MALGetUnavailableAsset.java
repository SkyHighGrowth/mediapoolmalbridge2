package MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of the response of {@link MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient}
 */
public class MALGetUnavailableAsset {

    /**
     * MAL asset id
     */
    @SerializedName("asset_id")
    private String assetId;

    /**
     * MAL property id
     */
    @SerializedName("property_id")
    private String propertyId;

    /**
     * asset urls
     */
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
