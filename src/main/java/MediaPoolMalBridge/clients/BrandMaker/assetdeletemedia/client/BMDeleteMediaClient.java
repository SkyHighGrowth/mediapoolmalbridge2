package MediaPoolMalBridge.clients.BrandMaker.assetdeletemedia.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdeletemedia.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.model.asset.Asset;
import com.brandmaker.webservices.mediapool.DeleteMediaArgument;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BMDeleteMediaClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDeleteMediaClient.class);

    private final AppConfig appConfig;

    public BMDeleteMediaClient(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public DeleteMediaResponse delete(final Asset asset) {
        try {
            final DeleteMediaArgument request = new DeleteMediaArgument();
            request.setMediaGuid(asset.getAssetId());
            final DeleteMediaResult response = getMediaPoolPort().deleteMedia(request);
            return new DeleteMediaResponse(response);
        } catch (final Exception e) {
            logger.error("Error deleting asset {}", asset, e);
            return new DeleteMediaResponse(false, e.getMessage());
        }
    }
}
