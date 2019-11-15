package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
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
public class BMUploadAssetService extends AbstractService {

    private final BMAssetMap bmAssetMap;

    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMUploadAssetService(final BMAssetMap bmAssetMap,
                                final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper )
    {
        this.bmAssetMap = bmAssetMap;
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void uploadAssets()
    {
        final Collection<TransferringAsset> bmAssets = bmAssetMap.values();
        bmAssets.stream()
                .filter( transferringAsset -> TransferringBMConnectionAssetStatus.UPLOADING.equals( transferringAsset.getTransferringBMConnectionAssetStatus() ) ||
                        ( TransferringMALConnectionAssetStatus.DOWNLOADED.equals(transferringAsset.getTransferringMALConnectionAssetStatus() ) &&
                        TransferringAssetStatus.MAL_UPDATED.equals( transferringAsset.getTransferringAssetStatus() ) ) )
                .forEach(transferringAsset -> {
                    if( transferringAsset instanceof BMAsset )
                    {
                        final BMAsset bmAsset = (BMAsset) transferringAsset;
                        taskExecutorWrapper.getTaskExecutor().execute( () -> bmUploadVersionAssetClient.upload(bmAsset));
                    } });
    }
}
