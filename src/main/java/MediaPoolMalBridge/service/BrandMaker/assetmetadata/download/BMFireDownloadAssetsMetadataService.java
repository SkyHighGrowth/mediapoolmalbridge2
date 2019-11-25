package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireDownloadAssetsMetadataService extends AbstractBMUniqueService {

    private final int pageSize = 1000;

    private final BMDownloadAssetMetadataService downloadAssetMetadataService;

    public BMFireDownloadAssetsMetadataService(final BMDownloadAssetMetadataService downloadAssetMetadataService )
    {
        this.downloadAssetMetadataService = downloadAssetMetadataService;
    }

    @Override
    protected void run()
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatus(
                    TransferringBMConnectionAssetStatus.METADATA_UPLOADED, TransferringBMConnectionAssetStatus.METADATA_DOWNLOADING, PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( downloadAssetMetadataService ) {
                                downloadAssetMetadataService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> downloadAssetMetadataService.start(bmAssetEntity));
            } );
        }
    }
}
