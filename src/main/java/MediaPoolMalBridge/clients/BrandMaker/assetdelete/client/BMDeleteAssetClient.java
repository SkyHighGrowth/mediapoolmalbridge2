package MediaPoolMalBridge.clients.BrandMaker.assetdelete.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.DeleteMediaArgument;
import com.brandmaker.webservices.mediapool.DeleteMediaResult;
import org.springframework.stereotype.Component;

@Component
public class BMDeleteAssetClient extends BrandMakerSoapClient {

    public DeleteMediaResponse delete(final BMAssetEntity bmAsset) {
        try {
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
            final DeleteMediaArgument request = new DeleteMediaArgument();
            request.setMediaGuid(bmAsset.getAssetId());
            final DeleteMediaResult result = getMediaPoolPort().deleteMedia(request);
            final DeleteMediaResponse response = new DeleteMediaResponse(result);
            if (response.isStatus()) {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETED);
            } else {
                reportErrorOnResponse(bmAsset.getAssetId(), response);
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
            }
            bmAssetRepository.save(bmAsset);
            return response;
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
            bmAssetRepository.save(bmAsset);
            return new DeleteMediaResponse(false, e.getMessage());
        }
    }
}
