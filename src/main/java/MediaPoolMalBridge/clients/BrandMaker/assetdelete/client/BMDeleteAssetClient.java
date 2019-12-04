package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.DeleteMediaArgument;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;
import org.springframework.stereotype.Component;

/**
 * Delete asset client class which encloses MediapoolWebServicePort.deleteMedia call
 * and transforms data
 */
@Component
public class BMDeleteAssetClient extends BrandMakerSoapClient {

    /**
     * Performs the call catches the exception and convert it to {@link DeleteMediaResponse} object
     * @param asset
     * @return
     */
    public DeleteMediaResponse delete(final AssetEntity asset) {
        try {
            final DeleteMediaArgument request = new DeleteMediaArgument();
            request.setMediaGuid(asset.getBmAssetId());
            final DeleteMediaResult result = getMediaPoolPort().deleteMedia(request);
            return new DeleteMediaResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(asset.getBmAssetId(), e);
            return new DeleteMediaResponse(false, e.getMessage());
        }
    }
}
