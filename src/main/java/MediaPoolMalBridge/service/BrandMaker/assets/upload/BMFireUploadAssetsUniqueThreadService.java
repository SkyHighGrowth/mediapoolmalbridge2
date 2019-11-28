package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadAssetsUniqueThreadService extends AbstractBMUniqueThreadService {

    private final static int pageSize = 2000;

    private final BMUploadAssetService bmUploadAssetService;

    public BMFireUploadAssetsUniqueThreadService(final BMUploadAssetService bmUploadAssetService) {
        this.bmUploadAssetService = bmUploadAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndTransferringConnectionAssetStatusAndUpdatedIsAfter(
                    TransferringBMConnectionAssetStatus.FILE_UPLOADING, TransferringBMConnectionAssetStatus.INITIALIZED, TransferringAssetStatus.MAL_UPDATED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadAssetService.start(bmAssetEntity)); }
            } );
        }
    }
}
