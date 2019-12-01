package MediaPoolMalBridge.clients.BrandMaker.assetid.client.model;

import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;

public class GetAssetIdFromMediaHashResponse extends AbstractBMResponse {

    private String assetId;

    public GetAssetIdFromMediaHashResponse(final String assetId ) {
        this.status = true;
        this.assetId = assetId;
    }

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
