package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.apache.commons.lang3.StringUtils;
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
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.FILE_DOWNLOADED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = assetEntities.hasNext();
            assetEntities.forEach( assetEntity -> {
                if(StringUtils.isNotBlank( assetEntity.getBmAssetId() ) && !assetEntity.getBmAssetId().startsWith( "CREATING_" ) ) {
                    if (taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax()) {
                        taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadAssetService.start(assetEntity));
                    }
                } } );
        }
    }
}
