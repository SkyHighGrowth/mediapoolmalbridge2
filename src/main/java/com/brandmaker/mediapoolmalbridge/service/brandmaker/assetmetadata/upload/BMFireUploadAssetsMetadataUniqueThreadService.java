package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.upload;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMUploadAssetMetadataService} on the given asset
 */
@Service
public class BMFireUploadAssetsMetadataUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireUploadAssetsMetadataUniqueThreadService(final BMUploadAssetMetadataService bmUploadAssetMetadataService ) {
        this.assetService = bmUploadAssetMetadataService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.FILE_UPLOADED,
                TransferringAssetStatus.METADATA_UPLOADING,
                x -> true );
    }
}
