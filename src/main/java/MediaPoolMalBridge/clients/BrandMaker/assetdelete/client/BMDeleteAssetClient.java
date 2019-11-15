package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client;

import MediaPoolMalBridge.AppConfig;
import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.model.asset.TransferringBMConnectionAssetStatus;
import com.brandmaker.webservices.mediapool.DeleteMediaArgument;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BMDeleteAssetClient extends BrandMakerSoapClient {

    private final static Logger logger = LoggerFactory.getLogger(BMDeleteAssetClient.class);

    private final AppConfig appConfig;

    public BMDeleteAssetClient(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public DeleteMediaResponse delete(final BMAsset bmAsset) {
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.DELETING);
            final DeleteMediaArgument request = new DeleteMediaArgument();
            request.setMediaGuid(bmAsset.getAssetId());
            final DeleteMediaResult result = getMediaPoolPort().deleteMedia(request);
            final DeleteMediaResponse response = new DeleteMediaResponse(result);
            if( response.isStatus() )
            {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.DELETED);
            }
            else
            {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.DELETING);
            }
            return  response;
        } catch (final Exception e) {
            logger.error("Error deleting asset {}", bmAsset, e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.DELETING);
            return new DeleteMediaResponse(false, e.getMessage());
        }
    }
}
