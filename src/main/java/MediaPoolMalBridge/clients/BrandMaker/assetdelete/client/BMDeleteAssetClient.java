package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.DeleteMediaArgument;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;
import org.springframework.stereotype.Component;

@Component
public class BMDeleteAssetClient extends BrandMakerSoapClient {

    public DeleteMediaResponse delete(final BMAssetEntity bmAsset) {
        try {

            final DeleteMediaArgument request = new DeleteMediaArgument();
            request.setMediaGuid(bmAsset.getAssetId());
            final DeleteMediaResult result = getMediaPoolPort().deleteMedia(request);
            return new DeleteMediaResponse(result);
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            return new DeleteMediaResponse(false, e.getMessage());
        }
    }
}
