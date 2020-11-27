package com.brandmaker.mediapoolmalbridge.service.brandmaker.assets.getassetid;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMGetAssetFromHashService} for specific asset
 */
@Service
public class BMFireGetAssetIdFromHashUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireGetAssetIdFromHashUniqueThreadService(final BMGetAssetFromHashService bmGetAssetFromHashService) {
        this.assetService = bmGetAssetFromHashService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.GET_BM_ASSET_ID,
                TransferringAssetStatus.GETTING_BM_ASSET_ID,
                MALAssetOperation.MAL_CREATED::equals );
    }
}
