package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMCreateAssetService extends AbstractBMNonUniqueService<BMAssetEntity> {

    private final BMUploadAssetClient bmUploadAssetClient;

    public BMCreateAssetService( final BMUploadAssetClient bmUploadAssetClient )
    {
        this.bmUploadAssetClient = bmUploadAssetClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
        final UploadStatus uploadStatus = bmUploadAssetClient.upload( bmAssetEntity );
        if (uploadStatus.isStatus()) {
            bmAssetEntity.setAssetId(uploadStatus.getBmAsset());
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATED);
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
        }
        bmAssetRepository.save(bmAssetEntity);
        synchronized ( this ) {
            notify();
        }
    }
}
