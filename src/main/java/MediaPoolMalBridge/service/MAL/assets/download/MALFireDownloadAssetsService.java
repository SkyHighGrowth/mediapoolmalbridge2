package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.model.asset.TransferringAsset;
import MediaPoolMalBridge.model.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class MALFireDownloadAssetsService extends AbstractService {

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadAssetService malDownloadAssetService;

    private final MALAssetMap malAssetMap;

    public MALFireDownloadAssetsService(final TaskExecutorWrapper taskExecutorWrapper,
                                        final MALDownloadAssetService malDownloadAssetService,
                                        final MALAssetMap malAssetMap )
    {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadAssetService = malDownloadAssetService;
        this.malAssetMap = malAssetMap;
    }

    public void downloadAssets() {

        final Collection<TransferringAsset> malAssets = malAssetMap.values();

        malAssets.stream()
                .filter( Objects::nonNull )
                .filter( transferringAsset -> TransferringAssetStatus.MAL_UPDATED.equals( transferringAsset.getTransferringAssetStatus() ) ||
                        TransferringAssetStatus.MAL_CREATED.equals( transferringAsset.getTransferringAssetStatus() ) )
                .forEach( transferringAsset -> {
                    if( transferringAsset instanceof MALAsset )
                    {
                        final MALAsset malAsset = (MALAsset) transferringAsset;
                        taskExecutorWrapper.getTaskExecutor()
                            .execute( () -> malDownloadAssetService.downloadMALAsset( malAsset ) );
                    } } );
    }
}
