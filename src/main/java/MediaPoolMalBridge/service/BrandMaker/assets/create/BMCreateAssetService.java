package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.clients.BrandMaker.model.BMAsset;
import MediaPoolMalBridge.model.BrandMaker.BMAssetMap;
import MediaPoolMalBridge.model.asset.TransferringAsset;
import MediaPoolMalBridge.model.asset.TransferringAssetStatus;
import MediaPoolMalBridge.model.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.model.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BMCreateAssetService extends AbstractService {

    private final BMAssetMap bmAssetMap;

    private final BMUploadAssetClient bmUploadAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMCreateAssetService(final BMAssetMap bmAssetMap,
                                final BMUploadAssetClient bmUploadAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper )
    {
        this.bmAssetMap = bmAssetMap;
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void createAssets()
    {
        final Collection<TransferringAsset> bmAssets = bmAssetMap.values();
        bmAssets.stream()
                .filter( transferringAsset -> TransferringBMConnectionAssetStatus.CREATING.equals( transferringAsset.getTransferringBMConnectionAssetStatus()) ||
                        ( TransferringAssetStatus.MAL_CREATED.equals( transferringAsset.getTransferringAssetStatus()) &&
                        TransferringMALConnectionAssetStatus.DOWNLOADED.equals( transferringAsset.getTransferringMALConnectionAssetStatus() ) ) )
                .forEach(transferringAsset -> {
                    if( transferringAsset instanceof BMAsset)
                    {
                        final BMAsset bmAsset = (BMAsset) transferringAsset;
                        taskExecutorWrapper.getTaskExecutor().execute( () -> bmUploadAssetClient.upload(bmAsset));
                    } });
    }
}
