package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetIdEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.BM.BMAssetIdRepository;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which creates asset on Mediapool service
 */
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
        final UploadStatus uploadStatus = bmUploadAssetClient.upload( assetEntity );
        if (uploadStatus.isStatus()) {
            onSuccess( assetEntity, uploadStatus.getBmAsset() );
        } else {
            onFailure( assetEntity, uploadStatus );
        }
    }

    @Transactional
    public void onSuccess( final AssetEntity assetEntity, final String bmAssetId ) {
        final BMAssetIdEntity bmAssetIdEntity = assetEntity.getBmAssetIdEntity();
        bmAssetIdEntity.setBmAssetId( bmAssetId );
        bmAssetIdRepository.save( bmAssetIdEntity );
        assetEntity.setBmAssetIdEntity( bmAssetIdEntity );
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
        assetRepository.save( assetEntity );
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
    }

    @Transactional
    protected void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse ) {
        if( !isGateOpen( assetEntity, "create asset", abstractBMResponse ) ) {
            return;
        }
        if( !triggerBMAssetIdDownloadFormHashIfPossible( assetEntity, abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
        }
        assetRepository.save( assetEntity );
    }

    @Override
    @Transactional
    protected boolean isGateOpen( final AssetEntity assetEntity, final String serviceDescription, final AbstractBMResponse abstractBMResponse ) {
        if( super.isGateOpen( assetEntity, serviceDescription, abstractBMResponse ) ) {
            return true;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        return false;
    }

    @Transactional
    protected boolean triggerBMAssetIdDownloadFormHashIfPossible( final AssetEntity assetEntity, final AbstractBMResponse uploadStatus ) {
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
