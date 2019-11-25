package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueService;
import org.springframework.stereotype.Service;

@Service
public class BMDeleteAssetService extends AbstractBMNonUniqueService<BMAssetEntity> {

    private final BMDeleteAssetClient bmDeleteAssetClient;

    public BMDeleteAssetService(final BMDeleteAssetClient bmDeleteAssetClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
        final DeleteMediaResponse deleteMediaResponse = bmDeleteAssetClient.delete( bmAssetEntity );
        if (deleteMediaResponse.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETED);
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), deleteMediaResponse);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
        }
        bmAssetRepository.save(bmAssetEntity);
        synchronized ( this ) {
            notify();
        }
    }
}
