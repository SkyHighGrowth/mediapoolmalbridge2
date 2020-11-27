package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.delete;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMDeleteAssetService} for specific asset
 */
@Service
public class BMFireDeleteAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireDeleteAssetsUniqueThreadService(final BMDeleteAssetService bmDeleteAssetService) {
        this.assetService = bmDeleteAssetService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.ASSET_ONBOARDED,
                TransferringAssetStatus.FILE_DELETING,
                MALAssetOperation.MAL_DELETED::equals );
    }
}
