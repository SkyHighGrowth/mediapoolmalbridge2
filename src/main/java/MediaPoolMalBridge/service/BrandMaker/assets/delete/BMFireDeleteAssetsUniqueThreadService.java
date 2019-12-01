package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMFireDeleteAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireDeleteAssetsUniqueThreadService(final BMDeleteAssetService bmDeleteAssetService) {
        this.assetService = bmDeleteAssetService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.ASSET_OBSERVED,
                TransferringAssetStatus.FILE_DELETING,
                TransferringAssetStatus.FILE_DELETED,
                MALAssetOperation.MAL_DELETED);
    }
}
