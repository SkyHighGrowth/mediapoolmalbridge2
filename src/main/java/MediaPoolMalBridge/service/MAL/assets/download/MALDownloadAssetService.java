package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadAssetService extends AbstractMALNonUniqueThreadService<AssetEntity> {

    private final MALDownloadAssetClient malDownloadAssetClient;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient) {
        this.malDownloadAssetClient = malDownloadAssetClient;
    }

    @Override
    protected void run( final AssetEntity assetEntity ) {
        assetEntity.increaseMalStatesRepetitions();
        final TransferringAssetStatus transferringAssetStatus = assetEntity.getTransferringAssetStatus();
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_DOWNLOADING );
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for metadata downloading achieved for asset id [%s]", assetEntity.getMalAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson( assetEntity ), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        try {
            final MALDownloadAssetResponse response = malDownloadAssetClient.download(decode(assetEntity.getUrl()), assetEntity.getFileNameOnDisc());
            logger.error( "RESPONSE {}", response);
            if ( response.isSuccess() ) {
                assetEntity.setMalStatesRepetitions( 0 );
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
            } else {
                assetEntity.setTransferringAssetStatus( transferringAssetStatus );
            }
        } catch (final Exception e) {
            final String message = String.format("Problem downloading asset with id [%s] and url [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, assetEntity.getMalGetAssetJson(), GSON.toJson( assetEntity ), null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
            assetEntity.setTransferringAssetStatus(transferringAssetStatus);
        }
        assetRepository.save(assetEntity);
    }

    private MALDownloadAssetResponse fire(final String url, final String fileName) {

        return malDownloadAssetClient.download(url, fileName);
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
