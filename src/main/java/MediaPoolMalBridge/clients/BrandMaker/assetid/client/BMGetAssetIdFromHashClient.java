package MediaPoolMalBridge.clients.BrandMaker.assetid.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetid.client.model.GetAssetIdFromMediaHashResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class BMGetAssetIdFromHashClient extends BrandMakerSoapClient {

    public GetAssetIdFromMediaHashResponse getAssetId(final AssetEntity asset) {
        try {
            final String result = getMediaPoolPort().getMediaGuidByHash(asset.getBmMd5Hash());
            return new GetAssetIdFromMediaHashResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new GetAssetIdFromMediaHashResponse(false, e.getMessage());
        }
    }
}
