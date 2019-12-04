package MediaPoolMalBridge.clients.BrandMaker.assetid.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;

/**
 * Wraps response of MediapoolWebServicePost.getMediaGuidByHash
 */
public class GetAssetIdFromMediaHashResponse extends AbstractBMResponse {

    /**
     * Represents Mediapool asset id
     */
    private String assetId;

    /**
     * Constructs from regular response
     * @param assetId
     */
    public GetAssetIdFromMediaHashResponse(final String assetId ) {
        this.status = true;
        this.assetId = assetId;
    }

    /**
     * Constructs from erroneous response
     * @param status - is always false
     * @param error
     */
    public GetAssetIdFromMediaHashResponse(final boolean status, final String error ) {
        this.status = status;
        errors.add(error);
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
