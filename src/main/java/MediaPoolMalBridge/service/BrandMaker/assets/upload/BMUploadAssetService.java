package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetService extends AbstractBMNonUniqueService<BMAssetEntity> {

    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    public BMUploadAssetService(final BMUploadVersionAssetClient bmUploadVersionAssetClient) {
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
        final UploadStatus uploadStatus = bmUploadVersionAssetClient.upload( bmAssetEntity );
        if (uploadStatus.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADED);
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
        }
        bmAssetRepository.save(bmAssetEntity);
        synchronized ( this ) {
            notify();
        }
    }
}
