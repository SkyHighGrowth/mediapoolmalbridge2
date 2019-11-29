package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireCreateAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    private final static int pageSize = 2000;

    private final BMCreateAssetService bmCreateAssetService;

    public BMFireCreateAssetsUniqueThreadService(final BMCreateAssetService bmCreateAssetService )
    {
        this.bmCreateAssetService = bmCreateAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.FILE_DOWNLOADED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = assetEntities.hasNext();
            assetEntities.forEach( assetEntity -> {
                if( assetEntity.getBmAssetId().startsWith( "CREATING_" ) ) {
                    if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                        taskExecutorWrapper.getTaskExecutor().execute(() -> bmCreateAssetService.start(assetEntity));
                } } } );
        }
    }
}
