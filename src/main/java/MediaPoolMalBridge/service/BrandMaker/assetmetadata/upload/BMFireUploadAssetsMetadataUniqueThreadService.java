package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
                x -> true );
    }

    protected List<AssetEntity> getAssetEntities( final TransferringAssetStatus fromStatus ) {
        return assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfterFetch(
                fromStatus, getMidnightBridgeLookInThePast(), PageRequest.of(0, appConfig.getDatabasePageSize()));
    }
}
