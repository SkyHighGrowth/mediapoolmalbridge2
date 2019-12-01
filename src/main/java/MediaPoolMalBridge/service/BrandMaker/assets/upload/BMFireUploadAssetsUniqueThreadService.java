package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.persistence.entity.enums.asset.MALAssetOperation;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireUploadAssetsUniqueThreadService(final BMUploadAssetService bmUploadAssetService) {
        this.assetService = bmUploadAssetService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.FILE_DOWNLOADED,
                TransferringAssetStatus.FILE_UPLOADING,
                TransferringAssetStatus.FILE_UPLOADED,
                MALAssetOperation.MAL_MODIFIED);
    }
}
