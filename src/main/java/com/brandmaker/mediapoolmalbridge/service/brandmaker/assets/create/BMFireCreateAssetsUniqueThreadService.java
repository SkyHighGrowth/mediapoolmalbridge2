package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.create;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service that execute {@link BMCreateAssetService} for the given asset
 */
@Service
public class BMFireCreateAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireCreateAssetsUniqueThreadService(final BMCreateAssetService bmCreateAssetService )
    {
        this.assetService = bmCreateAssetService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.FILE_DOWNLOADED,
                TransferringAssetStatus.FILE_UPLOADING,
                MALAssetOperation.MAL_CREATED::equals );
    }
}
