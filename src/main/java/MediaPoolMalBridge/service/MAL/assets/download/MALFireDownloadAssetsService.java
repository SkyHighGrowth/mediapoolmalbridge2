package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MALFireDownloadAssetsService extends AbstractMALUniqueService {

    private final int pageSize = 1000;

    private final MALDownloadAssetService malDownloadAssetService;

    public MALFireDownloadAssetsService(final MALDownloadAssetService malDownloadAssetService) {
        this.malDownloadAssetService = malDownloadAssetService;
    }

    @Override
    protected void run()
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringAssetStatusOrTransferringAssetStatus(TransferringAssetStatus.MAL_UPDATED,
                    TransferringAssetStatus.MAL_CREATED, PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach( malAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( malDownloadAssetService ) {
                                malDownloadAssetService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute( () -> malDownloadAssetService.start( malAssetEntity ) );

            } );
        }
    }
}
