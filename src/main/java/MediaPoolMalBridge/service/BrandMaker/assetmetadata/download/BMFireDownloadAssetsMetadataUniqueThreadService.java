package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
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
                TransferringAssetStatus.DONE,
                null);
    }
}
