package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BMFireCreateAssetsService extends AbstractBMUniqueService {

    private final int pageSize = 1000;

    private final BMCreateAssetService bmCreateAssetService;

    public BMFireCreateAssetsService( final BMCreateAssetService bmCreateAssetService )
    {
        this.bmCreateAssetService = bmCreateAssetService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<BMAssetEntity> bmAssetEntities = bmAssetRepository.findTransferringBMConnectionAssetStatusOrTransferringBMConnectionAssetStatusAndTransferringConnectionAssetStatusAndTransferringMALConnectionAssetStatus(
                    TransferringBMConnectionAssetStatus.FILE_CREATING, TransferringBMConnectionAssetStatus.INVALID,TransferringAssetStatus.MAL_CREATED, TransferringMALConnectionAssetStatus.DOWNLOADED, PageRequest.of(page, pageSize));
            condition = bmAssetEntities.hasNext();
            bmAssetEntities.forEach( bmAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( bmCreateAssetService ) {
                                bmCreateAssetService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> bmCreateAssetService.start(bmAssetEntity));
            } );
        }
    }
}
