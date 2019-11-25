package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireDeleteAssetsService extends AbstractBMUniqueService {

    private final int pageSize = 1000;

    private final BMDeleteAssetService bmDeleteAssetService;

    public BMFireDeleteAssetsService(final BMDeleteAssetService bmDeleteAssetService) {
        this.bmDeleteAssetService = bmDeleteAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findAllByTransferringBMConnectionAssetStatusOrTransferringConnectionAssetStatus(
                    TransferringBMConnectionAssetStatus.FILE_DELETING, TransferringAssetStatus.MAL_DELETED, PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( bmDeleteAssetService ) {
                                bmDeleteAssetService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> bmDeleteAssetService.start(bmAssetEntity));
            } );
        }
    }
}
