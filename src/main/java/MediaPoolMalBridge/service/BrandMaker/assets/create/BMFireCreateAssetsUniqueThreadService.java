package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
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
                TransferringAssetStatus.FILE_UPLOADED,
                MALAssetOperation.MAL_CREATED);
    }
}
