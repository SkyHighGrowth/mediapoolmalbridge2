package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.download.client.MALDownloadAssetClient;
import MediaPoolMalBridge.clients.MAL.download.client.model.MALDownloadAssetResponse;
import MediaPoolMalBridge.clients.MAL.singleresponse.MALAbstractResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.MAL.AbstractMALNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Service that triggers download of asset from MAL server
 */
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
            try {
                final MALDownloadAssetResponse response = malDownloadAssetClient.download(decode(assetEntity.getUrl()), assetEntity.getFileNameOnDisc());
                if (response.isSuccess()) {
                    onSuccess(assetEntity);
                } else {
                    onFailure(assetEntity, response, null);
                }
            } catch (final Exception e) {
                onFailure(assetEntity, null, e);
            }
            assetRepository.save(assetEntity);
    }

    private void onSuccess( final AssetEntity assetEntity ) {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
        assetRepository.save(assetEntity);
    }

    @Transactional
    protected void onFailure( final AssetEntity assetEntity, final MALAbstractResponse malAbstractResponse, final Exception e ) {
        if( !isGateOpen( assetEntity, "asset download", malAbstractResponse, e ) ) {
            return;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ASSET_ONBOARDED );
        assetRepository.save(assetEntity);
        final String message;
        if( e == null ) {
            message = String.format("Problem downloading asset with id [%s] and url [%s], with message [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl(), malAbstractResponse.getMessage());
        } else {
            message = String.format("Problem downloading asset with id [%s] and url [%s], with message [%s]", assetEntity.getMalAssetId(), assetEntity.getUrl(), e.getMessage() );
        }
        final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), assetEntity.getMalAssetId(), message, ReportTo.NONE, GSON.toJson( assetEntity ), null, null );
        reportsRepository.save( reportsEntity );
    }

    @Override
    @Transactional
    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final MALAbstractResponse malAbstractResponse, final Exception e ) {
        if( super.isGateOpen( assetEntity, serviceDescription, malAbstractResponse, e ) ) {
            return true;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        return false;
    }

    private String decode(final String encoded)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }
}
