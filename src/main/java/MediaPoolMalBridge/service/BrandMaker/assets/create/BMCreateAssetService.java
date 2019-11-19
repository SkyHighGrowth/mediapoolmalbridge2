package MediaPoolMalBridge.service.BrandMaker.assets.create;

import MediaPoolMalBridge.clients.BrandMaker.assetupload.client.BMUploadAssetClient;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BMCreateAssetService extends AbstractBMService {

    private final BMUploadAssetClient bmUploadAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMCreateAssetService(final BMUploadAssetClient bmUploadAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper) {
        this.bmUploadAssetClient = bmUploadAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void createAssets() {
        final List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findFileCreatingOrMalCreatedAndDownloaded(TransferringBMConnectionAssetStatus.FILE_CREATED,
                TransferringAssetStatus.MAL_CREATED, TransferringMALConnectionAssetStatus.DOWNLOADED );
        bmAssetEntities.forEach(bmAsset ->
                taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadAssetClient.upload(bmAsset)));
    }
}
