package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

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
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatusAndUpdatedIsAfter(
                    TransferringBMConnectionAssetStatus.FILE_DELETING, TransferringAssetStatus.MAL_DELETED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> bmDeleteAssetService.start(bmAssetEntity)); }
            } );
        }
    }
}
