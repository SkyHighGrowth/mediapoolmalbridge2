package MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client;

import MediaPoolMalBridge.clients.BrandMaker.BrandMakerSoapClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import com.brandmaker.webservices.mediapool.UploadMetadataResult;
import org.springframework.stereotype.Component;

@Component
public class BMUploadMetadataClient extends BrandMakerSoapClient {

    public UploadMetadataStatus upload(final BMAssetEntity bmAsset) {
        UploadMetadataStatus uploadMetadataStatus;
        bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING);
        try {
            final UploadMetadataResult result = getMediaPoolPort().uploadMetaData(bmAsset.getUploadMetadataArgument());
            uploadMetadataStatus = new UploadMetadataStatus(result);
            if (result.isSuccess()) {
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADED);
            } else {
                reportErrorOnResponse(bmAsset.getAssetId(), uploadMetadataStatus);
                bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING);
            }
        } catch (final Exception e) {
            reportErrorOnException(bmAsset.getAssetId(), e);
            bmAsset.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING);
            uploadMetadataStatus = new UploadMetadataStatus(false, e.getMessage());
        }
        bmAssetRepository.save(bmAsset);
        return uploadMetadataStatus;
    }
}
