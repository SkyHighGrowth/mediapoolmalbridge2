package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadAssetService extends AbstractMALNonUniqueThreadService<MALAssetEntity> {

    private final MALDownloadAssetClient malDownloadAssetClient;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient) {
        this.malDownloadAssetClient = malDownloadAssetClient;
    }

    @Override
    protected void run( final MALAssetEntity malAssetEntity ) {
        if( malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING, appConfig.getAssetStateRepetitionMax() ) ) {
            final String message = String.format( "Max retries for metadata downloading achieved for asset id [%s]", malAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson( malAssetEntity ), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( malAssetEntity ) );
            return;
        }
        try {
            final MALDownloadAssetResponse response = malDownloadAssetClient.download(decode(malAssetEntity.getUrl()), malAssetEntity.getFileNameOnDisc());
            if ( response.isSuccess() ) {
                malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADED);
            }
        } catch (final Exception e) {
            final String message = String.format("Problem downloading asset with id [%s] and url [%s]", malAssetEntity.getAssetId(), malAssetEntity.getUrl() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, malAssetEntity.getMalGetAssetJson(), GSON.toJson( malAssetEntity ), null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
            malAssetEntity.setTransferringMALConnectionAssetStatus(TransferringMALConnectionAssetStatus.DOWNLOADING, appConfig.getAssetStateRepetitionMax());
        }
        malAssetRepository.save(malAssetEntity);
    }

    private MALDownloadAssetResponse fire(final String url, final String fileName) {

        return malDownloadAssetClient.download(url, fileName);
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
