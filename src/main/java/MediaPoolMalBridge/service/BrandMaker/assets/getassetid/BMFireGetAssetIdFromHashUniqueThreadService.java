package MediaPoolMalBridge.service.BrandMaker.assets.getassetid;

import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMFireGetAssetIdFromHashUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireGetAssetIdFromHashUniqueThreadService(final BMGetAssetFromHashService bmGetAssetFromHashService) {
        this.assetService = bmGetAssetFromHashService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.GET_BM_ASSET_ID,
                TransferringAssetStatus.GETTING_BM_ASSET_ID,
                TransferringAssetStatus.FILE_UPLOADED,
                MALAssetOperation.MAL_CREATED);
    }
}
