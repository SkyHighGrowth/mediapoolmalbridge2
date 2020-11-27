package com.brandmaker.mediapoolmalbridge.service.mal.assets.download;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.MALAssetOperation;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.mal.AbstractMALUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Scheduler service that triggers execution of {@link MALDownloadAssetService}
 */
@Service
public class MALFireDownloadAssetsUniqueThreadService extends AbstractMALUniqueThreadService {

    public MALFireDownloadAssetsUniqueThreadService(final MALDownloadAssetService malDownloadAssetService) {
        this.assetService = malDownloadAssetService;
    }

    @Override
    protected void run()
    {
        executeTransition( TransferringAssetStatus.ASSET_ONBOARDED,
                TransferringAssetStatus.FILE_DOWNLOADING,
                x -> !MALAssetOperation.MAL_DELETED.equals( x ) );
    }
}
