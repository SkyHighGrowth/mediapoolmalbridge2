package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
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
public class BMCreateAssetService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadAssetClient bmUploadAssetClient;

    private final UploadedFileRepository uploadedFileRepository;

    public BMCreateAssetService( final BMUploadAssetClient bmUploadAssetClient,
                                 final UploadedFileRepository uploadedFileRepository)
    {
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        final TransferringAssetStatus transferringAssetStatus = assetEntity.getTransferringAssetStatus();
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_CREATING );
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for create asset achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            return;
        }
        final UploadStatus uploadStatus = bmUploadAssetClient.upload( assetEntity );
        if (uploadStatus.isStatus()) {
            assetEntity.getBmAssetIdEntity().setBmAssetId( uploadStatus.getBmAsset() );
            assetEntity.setMalStatesRepetitions( 0 );
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_CREATED);
            uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        } else {
            reportErrorOnResponse(assetEntity.getBmAssetId(), uploadStatus);
            assetEntity.setTransferringAssetStatus(transferringAssetStatus);
        }
        assetRepository.save(assetEntity);
    }
}
