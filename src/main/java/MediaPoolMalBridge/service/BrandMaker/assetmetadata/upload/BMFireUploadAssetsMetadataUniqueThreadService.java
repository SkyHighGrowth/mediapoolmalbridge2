package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadAssetsMetadataUniqueThreadService extends AbstractBMUniqueThreadService {

    private final static int pageSize = 2000;

    private final BMUploadAssetMetadataService bmUploadAssetMetadataService;

    public BMFireUploadAssetsMetadataUniqueThreadService(final BMUploadAssetMetadataService bmUploadAssetMetadataService ) {
        this.bmUploadAssetMetadataService = bmUploadAssetMetadataService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndUpdatedIsAfter(
                    TransferringBMConnectionAssetStatus.FILE_CREATED, TransferringBMConnectionAssetStatus.FILE_UPLOADED, TransferringBMConnectionAssetStatus.METADATA_UPLOADING, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadAssetMetadataService.start(bmAssetEntity)); }
            } );
        }
    }
}
