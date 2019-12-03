package MediaPoolMalBridge.service.Bridge.databasenormalizer;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.repository.Bridge.AssetRepository;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class BridgeDatabaseNormalizerService extends AbstractService {

    private AssetRepository assetRepository;

    public BridgeDatabaseNormalizerService(final AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @PostConstruct
    public void init() {
        normalize( getMidnight(), TransferringAssetStatus.FILE_DOWNLOADING, TransferringAssetStatus.ASSET_OBSERVED);
        normalize( getMidnight(), TransferringAssetStatus.FILE_UPLOADING, TransferringAssetStatus.FILE_DOWNLOADED );
        normalize( getMidnight(), TransferringAssetStatus.METADATA_UPLOADING, TransferringAssetStatus.FILE_UPLOADED );
        normalize( getMidnight(), TransferringAssetStatus.METADATA_DOWNLOADING, TransferringAssetStatus.METADATA_UPLOADED );
        normalize( getMidnight(), TransferringAssetStatus.GETTING_BM_ASSET_ID,  TransferringAssetStatus.GET_BM_ASSET_ID );
    }

    private void normalize(final LocalDateTime midnight, final TransferringAssetStatus from, TransferringAssetStatus to )
    {
        boolean condition = true;
        for(int page = 0; condition; ++page ) {
            Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(from, midnight, PageRequest.of(page, 200));
            condition = assetEntities.hasNext();
            assetEntities.forEach(assetEntity -> {
                assetEntity.setTransferringAssetStatus( to );
                assetRepository.save( assetEntity );
            });
        }
    }
}
