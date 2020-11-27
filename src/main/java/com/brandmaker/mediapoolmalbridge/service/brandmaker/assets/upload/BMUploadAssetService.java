package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.upload;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadversion.client.BMUploadVersionAssetClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadversion.client.model.UploadStatus;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.UploadedFileEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
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
    public void onSuccess( final AssetEntity assetEntity ) {
        assetEntity.setTransferringAssetStatus(TransferringAssetStatus.FILE_UPLOADED);
        assetEntity.setMalStatesRepetitions( 0 );
        assetRepository.save( assetEntity );
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
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
    public boolean isGateOpen(final AssetEntity assetEntity, final String serviceDescription, final AbstractBMResponse abstractBMResponse ) {
        if( super.isGateOpen( assetEntity, serviceDescription, abstractBMResponse ) ) {
            return true;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        return false;
    }
}
