package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
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
        executeTransition( TransferringAssetStatus.ASSET_OBSERVED,
                TransferringAssetStatus.FILE_DOWNLOADING,
                TransferringAssetStatus.FILE_DOWNLOADED,
                null);
    }
}
