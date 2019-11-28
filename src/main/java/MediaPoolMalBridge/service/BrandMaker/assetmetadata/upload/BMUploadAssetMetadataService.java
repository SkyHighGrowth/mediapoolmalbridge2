package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetMetadataService extends AbstractBMNonUniqueThreadService<BMAssetEntity> {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient ) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        if( bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_UPLOADING, appConfig.getAssetStateRepetitionMax() ) ) {
            final String message = String.format( "Max retries for metadata uploading achieved for asset id [%s]", bmAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(bmAssetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( bmAssetEntity ) );
            return;
        }
        final UploadMetadataStatus uploadMetadataStatus = bmUploadMetadataClient.upload( bmAssetEntity );
        if (uploadMetadataStatus.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus( TransferringBMConnectionAssetStatus.METADATA_UPLOADED );
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadMetadataStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus( TransferringBMConnectionAssetStatus.METADATA_UPLOADING );
        }
        bmAssetRepository.save(bmAssetEntity);
    }
}
