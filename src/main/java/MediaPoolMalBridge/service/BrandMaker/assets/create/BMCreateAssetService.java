package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BMCreateAssetService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadAssetClient bmUploadAssetClient;

    private final BMAssetIdRepository bmAssetIdRepository;

    private final UploadedFileRepository uploadedFileRepository;

    public BMCreateAssetService( final BMUploadAssetClient bmUploadAssetClient,
                                 final BMAssetIdRepository bmAssetIdRepository,
                                 final UploadedFileRepository uploadedFileRepository)
    {
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.bmAssetIdRepository = bmAssetIdRepository;
        this.uploadedFileRepository = uploadedFileRepository;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        assetEntity.increaseMalStatesRepetitions();
        if( assetEntity.getMalStatesRepetitions() > appConfig.getAssetStateRepetitionMax() ) {
            final String message = String.format( "Max retries for create asset achieved for asset id [%s]", assetEntity.getBmAssetId() );
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, GSON.toJson(assetEntity), null, null );
            reportsRepository.save( reportsEntity );
            logger.error( "message {}, asset {}", message, GSON.toJson( assetEntity ) );
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.ERROR );
            assetRepository.save( assetEntity );
            uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
            return;
        }
        final UploadStatus uploadStatus = bmUploadAssetClient.upload( assetEntity );
        if (uploadStatus.isStatus()) {
            final BMAssetIdEntity bmAssetIdEntity = assetEntity.getBmAssetIdEntity();
            bmAssetIdEntity.setBmAssetId( uploadStatus.getBmAsset() );
            bmAssetIdRepository.save( bmAssetIdEntity );
            assetEntity.getBmAssetIdEntity().setBmAssetId( uploadStatus.getBmAsset() );
            assetEntity.setMalStatesRepetitions( 0 );
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
            uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        } else {
            if( !triggerBMAssetIdDownloadFormHashIfPossible( assetEntity, uploadStatus ) ) {
                reportErrorOnResponse(assetEntity.getBmAssetId(), uploadStatus);
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
            }
        }
        assetRepository.save(assetEntity);
    }

    private boolean triggerBMAssetIdDownloadFormHashIfPossible( final AssetEntity assetEntity, final UploadStatus uploadStatus ) {
        if( uploadStatus.getErrors() == null ||
            uploadStatus.getErrors().isEmpty() ) {
            return false;
        }

        for( final String error : uploadStatus.getErrors() ) {
            if( error.startsWith( "The media already exists!" ) ) {
                final String bmAssetId = StringUtils.substringAfter( error, "The media already exists!" );
                assetEntity.setBmMd5Hash( bmAssetId.trim() );
                assetEntity.setTransferringAssetStatus( TransferringAssetStatus.GET_BM_ASSET_ID );
                return true;
            }
        }
        return false;
    }
}
