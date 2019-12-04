package MediaPoolMalBridge.clients.MAL.asset.client.model;

import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MAL server get assets response for the client {@link MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient}
 */
public class MALGetAssetsResponse extends MALAbstractResponse {

    /**
     * total assets in MAL server
     */
    @SerializedName("total_assets")
    private String totalAssets;

    /**
     * total pages according to specified perPage attribute in request
     */
    @SerializedName("total_pages")
    private String totalPages;

    /**
     * List representing assets obtained in response
     */
    private List<MALGetAsset> assets;

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<MALGetAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<MALGetAsset> assets) {
        this.assets = assets;
    }
}
