package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class BMFireDeleteAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    private final static int pageSize = 2000;

    private final BMDeleteAssetService bmDeleteAssetService;

    public BMFireDeleteAssetsUniqueThreadService(final BMDeleteAssetService bmDeleteAssetService) {
        this.bmDeleteAssetService = bmDeleteAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.ASSET_OBSERVED_DELETION, getTodayMidnight(), PageRequest.of(page, pageSize));
            final CountDownLatch latch = new CountDownLatch( assetEntities.getNumberOfElements() );
            condition = assetEntities.hasNext();
            assetEntities.forEach( assetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> bmDeleteAssetService.start(assetEntity));
                } else {
                    latch.countDown();
                }
            } );
            try {
                latch.await();
            } catch( final InterruptedException e ) {
                Thread.interrupted();
                throw new RuntimeException();
            }
        }
    }
}
