package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.model.BrandMaker.BMAssetMap;
import MediaPoolMalBridge.model.asset.TransferringAsset;
import MediaPoolMalBridge.model.asset.TransferringAssetStatus;
import MediaPoolMalBridge.model.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BMDeleteAssetService extends AbstractService {

    private final BMAssetMap bmAssetMap;

    private final BMDeleteAssetClient bmDeleteAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMDeleteAssetService(final BMAssetMap bmAssetMap,
                                final BMDeleteAssetClient bmDeleteAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper )
    {
        this.bmAssetMap = bmAssetMap;
        this.bmDeleteAssetClient = bmDeleteAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void deleteAssets()
    {
        final Collection<TransferringAsset> bmAssets = bmAssetMap.values();
        bmAssets.stream()
                .filter( transferringAsset -> TransferringBMConnectionAssetStatus.DELETING.equals( transferringAsset.getTransferringBMConnectionAssetStatus() ) ||
                        TransferringAssetStatus.MAL_DELETED.equals( transferringAsset.getTransferringAssetStatus() ) )
                .forEach(transferringAsset -> {
                    if( transferringAsset instanceof BMAsset)
                    {
                        final BMAsset bmAsset = (BMAsset) transferringAsset;
                        taskExecutorWrapper.getTaskExecutor().execute( () -> bmDeleteAssetClient.delete(bmAsset));
                    } });
    }
}
