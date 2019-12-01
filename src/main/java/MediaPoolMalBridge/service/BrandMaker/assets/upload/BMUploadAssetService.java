package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    public BMUploadAssetService(final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                                final UploadedFileRepository uploadedFileRepository) {
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for uploading asset achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        final UploadStatus uploadStatus = bmUploadVersionAssetClient.upload( assetEntity );
        if (uploadStatus.isStatus()) {
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
            assetEntity.setMalStatesRepetitions( 0 );
            uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        } else {
            reportErrorOnResponse(assetEntity.getBmAssetId(), uploadStatus);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
        }
        assetRepository.save(assetEntity);
    }
}
