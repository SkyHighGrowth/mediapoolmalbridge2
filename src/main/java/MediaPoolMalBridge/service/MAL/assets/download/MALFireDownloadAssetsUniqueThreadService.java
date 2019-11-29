package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MALFireDownloadAssetsUniqueThreadService extends AbstractMALUniqueThreadService {

    private final static int pageSize = 2000;

    private final MALDownloadAssetService malDownloadAssetService;

    public MALFireDownloadAssetsUniqueThreadService(final MALDownloadAssetService malDownloadAssetService) {
        this.malDownloadAssetService = malDownloadAssetService;
    }

    @Override
    protected void run()
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusOrTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.ASSET_OBSERVED_CREATION, TransferringAssetStatus.ASSET_OBSERVED_MODIFICATION, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = assetEntities.hasNext();
            assetEntities.forEach( assetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetService.start( assetEntity ) ); }
            } );
        }
    }
}
