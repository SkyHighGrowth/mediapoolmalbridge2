package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.model.UploadStatus;
import MediaPoolMalBridge.clients.BrandMaker.model.response.AbstractBMResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.Bridge.UploadedFileEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.UploadedFileRepository;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which uploads asset to Mediapool server
 */
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
        if( assetEntity.getBmAssetId().startsWith( "CREATING_ ") ) {
            return;
        }
        final UploadStatus uploadStatus = bmUploadVersionAssetClient.upload(assetEntity);
        if (uploadStatus.isStatus()) {
            onSuccess(assetEntity);
        } else {
            onFailure(assetEntity, uploadStatus);
        }
    }

    @Transactional
    protected void onSuccess( final AssetEntity assetEntity ) {
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
        assetEntity.setMalStatesRepetitions( 0 );
        assetRepository.save( assetEntity );
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
    }

    @Transactional
    protected void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        boolean duplicated = false;
        for( final String error : abstractBMResponse.getErrors() ) {
            if ("Duplicated media!".equals(error.trim())) {
                duplicated = true;
                break;
            }
        }
        reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
        if( duplicated ) {
            assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
            assetRepository.save( assetEntity );
        } else {
            if( isGateOpen( assetEntity, "uploading asset", abstractBMResponse ) ) {
                assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_DOWNLOADED);
                assetRepository.save(assetEntity);
            }
        }
    }

    @Override
    @Transactional
    protected boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final AbstractBMResponse abstractBMResponse ) {
        if( super.isGateOpen( assetEntity, serviceDescription, abstractBMResponse ) ) {
            return true;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        return false;
    }
}
