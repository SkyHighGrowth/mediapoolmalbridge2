package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class MALDownloadAssetService extends AbstractMALNonUniqueThreadService<AssetEntity> {

    private final MALDownloadAssetClient malDownloadAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    public MALDownloadAssetService(final MALDownloadAssetClient malDownloadAssetClient,
                                   final UploadedFileRepository uploadedFileRepository) {
        this.malDownloadAssetClient = malDownloadAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run( final AssetEntity assetEntity ) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for asset download achieved for asset id [%s]", assetEntity.getMalAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson( assetEntity ), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        try {
            final MALDownloadAssetResponse response = malDownloadAssetClient.download(decode(assetEntity.getUrl()), assetEntity.getFileNameOnDisc());
            logger.debug( "RESPONSE {}", response);
            if ( response.isSuccess() ) {
                assetEntity.setMalStatesRepetitions( 0 );
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
            } else {
                uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
                assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ASSET_OBSERVED );
            }
        } catch (final Exception e) {
            uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
            final String message = String.format("Problem downloading asset with id [%s] and url [%s], with message [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl(), e.getMessage() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.MAL, assetEntity.getMalGetAssetJson(), GSON.toJson( assetEntity ), null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.ASSET_OBSERVED);
        }
        assetRepository.save(assetEntity);
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
