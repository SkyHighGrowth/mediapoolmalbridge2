package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMUploadAssetService extends AbstractBMNonUniqueThreadService<BMAssetEntity> {

    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    public BMUploadAssetService(final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                                final UploadedFileRepository uploadedFileRepository) {
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        if( bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING, appConfig.getAssetStateRepetitionMax() ) ) {
            final String message = String.format( "Max retries for uploading asset achieved for asset id [%s]", bmAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(bmAssetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( bmAssetEntity ) );
            return;
        }
        final UploadStatus uploadStatus = bmUploadVersionAssetClient.upload( bmAssetEntity );
        if (uploadStatus.isStatus()) {
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADED);
            uploadedFileRepository.save( new UploadedFileEntity( bmAssetEntity.getFileName() ) );
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_UPLOADING);
        }
        bmAssetRepository.save(bmAssetEntity);
    }
}
