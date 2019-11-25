package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.GetMediaDetailsArgument;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;
import org.springframework.stereotype.Component;

@Component
public class BMDownloadMediaDetailsClient extends BrandMakerSoapClient {

    public DownloadMediaDetailsResponse download(final BMAssetEntity bmAsset) {
        try {
            final GetMediaDetailsArgument request = new GetMediaDetailsArgument();
            request.setMediaGuid(bmAsset.getAssetId());
            final GetMediaDetailsResult result = getMediaPoolPort().getMediaDetails(request);
            final DownloadMediaDetailsResponse response = new DownloadMediaDetailsResponse(result);
            return response;
        } catch (final Exception e) {
            logger.error("Error downloading asset {}", bmAsset, e);
            reportErrorOnException( bmAsset.getAssetId(), e );
            return new DownloadMediaDetailsResponse(false, e.getMessage());
        }
    }
}
