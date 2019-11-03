package MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.model.asset.Asset;
import com.brandmaker.webservices.mediapool.GetMediaDetailsArgument;
import com.brandmaker.webservices.mediapool.GetMediaDetailsResult;
import com.brandmaker.webservices.mediapool.MediaPoolWebServicePort;
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

    public DownloadMediaDetailsResponse download(final Asset asset) {
        try {
            final GetMediaDetailsArgument request = new GetMediaDetailsArgument();
            request.setMediaGuid(asset.getAssetId());
            final GetMediaDetailsResult response = getMediaPoolPort().getMediaDetails(request);
            return new DownloadMediaDetailsResponse(response);
        } catch (final Exception e) {
            logger.error("Error downloading asset {}", asset, e);
            return new DownloadMediaDetailsResponse(false, e.getMessage());
        }
    }
}
