package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.upload;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMUploadAssetService} for specific asset
 */
@Service
public class BMFireUploadAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireUploadAssetsUniqueThreadService(final BMUploadAssetService bmUploadAssetService) {
        this.assetService = bmUploadAssetService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.FILE_DOWNLOADED,
                TransferringAssetStatus.FILE_UPLOADING,
                MALAssetOperation.MAL_MODIFIED::equals );
    }
}
