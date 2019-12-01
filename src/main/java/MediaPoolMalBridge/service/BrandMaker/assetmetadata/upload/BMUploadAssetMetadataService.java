package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetMetadataService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient ) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for metadata uploading achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            return;
        }
        final UploadMetadataStatus uploadMetadataStatus = bmUploadMetadataClient.upload( assetEntity );
        if (uploadMetadataStatus.isStatus()) {
            assetEntity.setMalStatesRepetitions( 0 );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_UPLOADED );
        } else {
            reportErrorOnResponse(assetEntity.getBmAssetId(), uploadMetadataStatus);
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_UPLOADED );
        }
        assetRepository.save(assetEntity);
    }
}
