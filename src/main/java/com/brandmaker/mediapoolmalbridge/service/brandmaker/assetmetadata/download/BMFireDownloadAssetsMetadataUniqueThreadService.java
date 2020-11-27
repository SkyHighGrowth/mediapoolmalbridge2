package com.brandmaker.mediapoolmalbridge.service.brandmaker.assetmetadata.download;

import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.asset.TransferringAssetStatus;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMDownloadAssetMetadataService} on the given asset
 */
@Service
public class BMFireDownloadAssetsMetadataUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireDownloadAssetsMetadataUniqueThreadService(final BMDownloadAssetMetadataService downloadAssetMetadataService )
    {
        this.assetService = downloadAssetMetadataService;
    }

    @Override
    protected void run()
    {
        executeTransition( TransferringAssetStatus.METADATA_UPLOADED,
                TransferringAssetStatus.METADATA_DOWNLOADING,
                x -> true );
    }
}
