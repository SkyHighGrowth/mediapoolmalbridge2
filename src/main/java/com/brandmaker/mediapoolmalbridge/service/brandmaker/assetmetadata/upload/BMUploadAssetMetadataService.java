package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.upload;

import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadmetadata.client.BMUploadMetadataClient;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.assetuploadmetadata.client.model.UploadMetadataStatus;
import com.brandmaker.mediapoolmalbridge.clients.brandmaker.model.response.AbstractBMResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMNonUniqueThreadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which uploads asset metadata
 */
@Service
public class BMUploadAssetMetadataService extends AbstractBMNonUniqueThreadService<AssetEntity> {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient ) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
    }

    @Override
    protected void run(final AssetEntity assetEntity) {
        final UploadMetadataStatus uploadMetadataStatus = bmUploadMetadataClient.upload( assetEntity );
        if (uploadMetadataStatus.isStatus()) {
            onSuccess( assetEntity );
        } else {
            onFailure( assetEntity, uploadMetadataStatus );
        }
    }

    @Transactional
    public void onSuccess( final AssetEntity assetEntity ) {
        assetEntity.setMalStatesRepetitions( 0 );
        assetEntity.setTransferringAssetStatus( TransferringAssetStatus.METADATA_UPLOADED );
        assetRepository.save(assetEntity);
    }

    @Transactional
    public void onFailure(final AssetEntity assetEntity, final AbstractBMResponse abstractBMResponse) {
        if( isGateOpen( assetEntity, "metadata uploading", abstractBMResponse ) ) {
            reportErrorOnResponse(assetEntity.getBmAssetId(), abstractBMResponse);
            assetEntity.setTransferringAssetStatus( TransferringAssetStatus.FILE_UPLOADED );
            assetRepository.save(assetEntity);
        }
    }
}
