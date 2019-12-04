package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service which executes {@link BMUploadAssetMetadataService} on the given asset
 */
@Service
public class BMFireUploadAssetsMetadataUniqueThreadService extends AbstractBMUniqueThreadService {

    public BMFireUploadAssetsMetadataUniqueThreadService(final BMUploadAssetMetadataService bmUploadAssetMetadataService ) {
        this.assetService = bmUploadAssetMetadataService;
    }

    @Override
    protected void run() {
        executeTransition( TransferringAssetStatus.FILE_UPLOADED,
                TransferringAssetStatus.METADATA_UPLOADING,
                TransferringAssetStatus.METADATA_UPLOADED,
                null);
    }
}
