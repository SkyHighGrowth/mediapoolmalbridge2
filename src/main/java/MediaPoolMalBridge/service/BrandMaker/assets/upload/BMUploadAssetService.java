package MediaPoolMalBridge.service.BrandMaker.assets.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadversion.client.BMUploadVersionAssetClient;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringMALConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BMUploadAssetService extends AbstractBMService {

    private final BMUploadVersionAssetClient bmUploadVersionAssetClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMUploadAssetService(final BMUploadVersionAssetClient bmUploadVersionAssetClient,
                                final TaskExecutorWrapper taskExecutorWrapper) {
        this.bmUploadVersionAssetClient = bmUploadVersionAssetClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void uploadAssets() {
        final List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findFileUploadingOrDownloadedAndMalUpdated( TransferringBMConnectionAssetStatus.FILE_UPLOADING,
                TransferringAssetStatus.MAL_UPDATED, TransferringMALConnectionAssetStatus.DOWNLOADED );

        bmAssetEntities.forEach(bmAssetEntity ->
                taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadVersionAssetClient.upload(bmAssetEntity)));
    }
}
