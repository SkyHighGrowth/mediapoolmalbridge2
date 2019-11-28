package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
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
public class BMCreateAssetService extends AbstractBMNonUniqueThreadService<BMAssetEntity> {

    private final BMUploadAssetClient bmUploadAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    public BMCreateAssetService( final BMUploadAssetClient bmUploadAssetClient,
                                 final UploadedFileRepository uploadedFileRepository)
    {
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run(BMAssetEntity bmAssetEntity) {
        if( bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING, appConfig.getAssetStateRepetitionMax() ) ) {
            final String message = String.format( "Max retries for create asset achieved for asset id [%s]", bmAssetEntity.getAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(bmAssetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, bmAsset {}", message, GSON.toJson( bmAssetEntity ) );
            return;
        }
        final UploadStatus uploadStatus = bmUploadAssetClient.upload( bmAssetEntity );
        if (uploadStatus.isStatus()) {
            bmAssetEntity.setAssetId(uploadStatus.getBmAsset());
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATED);
            uploadedFileRepository.save( new UploadedFileEntity( bmAssetEntity.getFileName() ) );
        } else {
            reportErrorOnResponse(bmAssetEntity.getAssetId(), uploadStatus);
            bmAssetEntity.setTransferringBMConnectionAssetStatus(TransferringBMConnectionAssetStatus.FILE_CREATING);
        }
        bmAssetRepository.save(bmAssetEntity);
    }
}
