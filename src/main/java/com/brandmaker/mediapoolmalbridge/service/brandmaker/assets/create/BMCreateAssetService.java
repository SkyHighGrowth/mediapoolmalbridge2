package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.create;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetupload.client.BMUploadAssetClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadversion.client.model.UploadStatus;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bm.BMAssetIdEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.UploadedFileEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bm.BMAssetIdRepository;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.UploadedFileRepository;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
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
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse ) {
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
    public boolean isGateOpen( final AssetEntity assetEntity, final String serviceDescription, final AbstractBMResponse abstractBMResponse ) {
        if( super.isGateOpen( assetEntity, serviceDescription, abstractBMResponse ) ) {
            return true;
        }
        uploadedFileRepository.save( new UploadedFileEntity( assetEntity.getFileNameOnDisc() ) );
        return false;
    }

    @Transactional
    public boolean triggerBMAssetIdDownloadFormHashIfPossible( final AssetEntity assetEntity, final AbstractBMResponse uploadStatus ) {
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
