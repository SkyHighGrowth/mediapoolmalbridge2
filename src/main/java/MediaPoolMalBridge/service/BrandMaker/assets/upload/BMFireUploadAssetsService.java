package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireUploadAssetsService extends AbstractBMUniqueService {

    private final int pageSize = 1000;

    private final BMUploadAssetService bmUploadAssetService;

    public BMFireUploadAssetsService(final BMUploadAssetService bmUploadAssetService) {
        this.bmUploadAssetService = bmUploadAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatusAndTransferringMALConnectionAssetStatus(
                    TransferringBMConnectionAssetStatus.FILE_UPLOADING, TransferringAssetStatus.MAL_UPDATED, TransferringMALConnectionAssetStatus.DOWNLOADED, PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( bmUploadAssetService ) {
                                bmUploadAssetService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> bmUploadAssetService.start(bmAssetEntity));
            } );
        }
    }
}
