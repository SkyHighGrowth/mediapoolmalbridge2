package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.model.DeleteMediaResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMDeleteAssetService extends AbstractBMNonUniqueThreadService<BMAssetEntity> {

    private final BMDeleteAssetClient bmDeleteAssetClient;

    public BMDeleteAssetService(final BMDeleteAssetClient bmDeleteAssetClient) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        if( bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING, appConfig.getAssetStateRepetitionMax() ) ) {
            final String message = String.format( "Max retries for delete asset achieved for asset id [%s]", bmAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(bmAssetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( bmAssetEntity ) );
            return;
        }
        final DeleteMediaResponse deleteMediaResponse = bmDeleteAssetClient.delete( bmAssetEntity );
        if (deleteMediaResponse.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETED);
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), deleteMediaResponse);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_DELETING);
        }
        bmAssetRepository.save(bmAssetEntity);
    }
}
