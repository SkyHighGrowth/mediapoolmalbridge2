package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import com.brandmaker.webservices.mediapool.GetMediaDetailsArgument;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;
import org.springframework.stereotype.Component;

@Component
public class BMDownloadMediaDetailsClient extends BrandMakerSoapClient {

    public DownloadMediaDetailsResponse download(final AssetEntity asset) {
        try {
            final GetMediaDetailsArgument request = new GetMediaDetailsArgument();
            request.setMediaGuid(asset.getBmAssetId());
            final GetMediaDetailsResult result = getMediaPoolPort().getMediaDetails(request);
            return new DownloadMediaDetailsResponse(result);
        } catch (final Exception e) {
            logger.error("Error downloading asset {}", asset, e);
            reportErrorOnException( asset.getBmAssetId(), e );
            return new DownloadMediaDetailsResponse(false, e.getMessage());
        }
    }
}
