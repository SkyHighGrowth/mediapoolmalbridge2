package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MALFireDownloadAssetsUniqueThreadService extends AbstractMALUniqueThreadService {

    private final static int pageSize = 2000;

    private final MALDownloadAssetService malDownloadAssetService;

    public MALFireDownloadAssetsUniqueThreadService(final MALDownloadAssetService malDownloadAssetService) {
        this.malDownloadAssetService = malDownloadAssetService;
    }

    @Override
    protected void run()
    {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringMALConnectionAssetStatusOrTransferringMALConnectionAssetStatusAndUpdatedIsAfter(
                    TransferringMALConnectionAssetStatus.OBSERVED, TransferringMALConnectionAssetStatus.DOWNLOADING, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach( malAssetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute( () -> malDownloadAssetService.start( malAssetEntity ) ); }
            } );
        }
    }
}
