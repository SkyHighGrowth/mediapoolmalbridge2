package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import com.brandmaker.webservices.mediapool.GetMediaDetailsArgument;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BMDownloadMediaDetailsClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDownloadMediaDetailsClient.class);

    private final AppConfig appConfig;

    public BMDownloadMediaDetailsClient(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public DownloadMediaDetailsResponse download(final BMAsset bmAsset) {
        try {
            final GetMediaDetailsArgument request = new GetMediaDetailsArgument();
            request.setMediaGuid(bmAsset.getAssetId());
            final GetMediaDetailsResult response = getMediaPoolPort().getMediaDetails(request);
            return new DownloadMediaDetailsResponse(response);
        } catch (final Exception e) {
            logger.error("Error downloading asset {}", bmAsset, e);
            return new DownloadMediaDetailsResponse(false, e.getMessage());
        }
    }
}
