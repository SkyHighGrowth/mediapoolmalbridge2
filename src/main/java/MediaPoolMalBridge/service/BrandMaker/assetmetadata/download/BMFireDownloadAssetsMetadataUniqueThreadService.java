package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.persistence.entity.Bridge.AssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireDownloadAssetsMetadataUniqueThreadService extends AbstractBMUniqueThreadService {

    private final static int pageSize = 2000;

    private final BMDownloadAssetMetadataService downloadAssetMetadataService;

    public BMFireDownloadAssetsMetadataUniqueThreadService(final BMDownloadAssetMetadataService downloadAssetMetadataService )
    {
        this.downloadAssetMetadataService = downloadAssetMetadataService;
    }

    @Override
    protected void run()
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<AssetEntity> assetEntities = assetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.METADATA_UPLOADED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = assetEntities.hasNext();
            assetEntities.forEach( assetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> downloadAssetMetadataService.start(assetEntity));
                }
            } );
        }
    }
}
