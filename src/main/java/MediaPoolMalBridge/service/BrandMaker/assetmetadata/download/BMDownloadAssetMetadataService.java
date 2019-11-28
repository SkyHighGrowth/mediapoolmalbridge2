package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMDownloadAssetMetadataService extends AbstractBMNonUniqueThreadService<BMAssetEntity> {

    private final BMDownloadMediaDetailsClient downloadMediaDetailsClient;

    public BMDownloadAssetMetadataService(final BMDownloadMediaDetailsClient downloadMediaDetailsClient )
    {
        this.downloadMediaDetailsClient = downloadMediaDetailsClient;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        if( bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_DOWNLOADING, appConfig.getAssetStateRepetitionMax()) ) {
            final String message = String.format( "Max retries for metadata downloading achieved for asset id [%s]", bmAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(bmAssetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( bmAssetEntity ) );
            return;
        }
        final DownloadMediaDetailsResponse downloadMediaDetailsResponse = downloadMediaDetailsClient.download( bmAssetEntity );
        if (!downloadMediaDetailsResponse.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus( TransferringBMConnectionAssetStatus.ERROR );
            reportErrorOnResponse(bmAssetEntity.getAssetId(), downloadMediaDetailsResponse);
            bmAssetRepository.save(bmAssetEntity);
        } else {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.METADATA_DOWNLOADED);
            bmAssetEntity.setBmHash( downloadMediaDetailsResponse.getGetMediaDetailsResult().getMediaHash() );
        }
        bmAssetRepository.save(bmAssetEntity);
    }
}
