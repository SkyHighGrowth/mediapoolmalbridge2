package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.model.DownloadMediaDetailsResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMDownloadAssetMetadataService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMDownloadMediaDetailsClient downloadMediaDetailsClient;

    public BMDownloadAssetMetadataService(final BMDownloadMediaDetailsClient downloadMediaDetailsClient )
    {
        this.downloadMediaDetailsClient = downloadMediaDetailsClient;
    }

    @Override
    protected void run(AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        final TransferringAssetStatus transferringAssetStatus = assetEntity.getTransferringAssetStatus();
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_DOWNLOADING );
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for metadata downloading achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        final DownloadMediaDetailsResponse downloadMediaDetailsResponse = downloadMediaDetailsClient.download( assetEntity );
        if (!downloadMediaDetailsResponse.isStatus() ) {
            assetEntity.setTransferringAssetStatus( transferringAssetStatus );
            reportErrorOnResponse(assetEntity.getBmAssetId(), downloadMediaDetailsResponse);
        } else {
            assetEntity.setMalStatesRepetitions( 0 );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_DOWNLOADED );
            assetEntity.setBmMd5Hash( downloadMediaDetailsResponse.getGetMediaDetailsResult().getMediaHash() );
        }
        assetRepository.save(assetEntity);
    }
}
