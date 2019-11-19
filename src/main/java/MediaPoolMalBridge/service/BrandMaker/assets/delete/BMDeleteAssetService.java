package MediaPoolMalBridge.service.BrandMaker.assets.delete;

import MediaPoolMalBridge.clients.BrandMaker.assetdelete.client.BMDeleteAssetClient;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BMDeleteAssetService extends AbstractBMService {

    private final BMDeleteAssetClient bmDeleteAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMDeleteAssetService(final BMDeleteAssetClient bmDeleteAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper) {
        this.bmDeleteAssetClient = bmDeleteAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void deleteAssets() {
        final List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findFileDeletingOrMalDeleted( TransferringBMConnectionAssetStatus.FILE_DELETING, TransferringAssetStatus.MAL_DELETED );
        bmAssetEntities.forEach(bmAsset ->
                taskExecutorWrapper.getTaskExecutor().execute(() -> bmDeleteAssetClient.delete(bmAsset)));
    }
}
