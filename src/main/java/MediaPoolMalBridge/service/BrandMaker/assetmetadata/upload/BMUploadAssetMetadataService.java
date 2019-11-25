package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetMetadataService extends AbstractBMNonUniqueService<BMAssetEntity> {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient ) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING);
        final UploadMetadataStatus uploadMetadataStatus = bmUploadMetadataClient.upload( bmAssetEntity );
        if (uploadMetadataStatus.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADED);
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadMetadataStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING);
        }
        bmAssetRepository.save(bmAssetEntity);
        synchronized ( this ) {
            notify();
        }
    }
}
