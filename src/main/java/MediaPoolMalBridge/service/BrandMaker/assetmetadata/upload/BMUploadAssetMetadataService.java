package MediaPoolMalBridge.service.BrandMaker.assetmetadata.upload;

import MediaPoolMalBridge.clients.BrandMaker.assetuploadmetadata.client.BMUploadMetadataClient;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BMUploadAssetMetadataService extends AbstractBMService {

    private final BMUploadMetadataClient bmUploadMetadataClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMUploadAssetMetadataService(final BMUploadMetadataClient bmUploadMetadataClient,
                                        final TaskExecutorWrapper taskExecutorWrapper) {
        this.bmUploadMetadataClient = bmUploadMetadataClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void upload() {
        List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findFileUploadedOrMetadataUploading( TransferringBMConnectionAssetStatus.FILE_UPLOADED, TransferringBMConnectionAssetStatus.METADATA_UPLOADING );
        bmAssetEntities.forEach(bmAsset -> {
            taskExecutorWrapper.getTaskExecutor().execute(() -> bmUploadMetadataClient.upload(bmAsset));
        });
    }
}
