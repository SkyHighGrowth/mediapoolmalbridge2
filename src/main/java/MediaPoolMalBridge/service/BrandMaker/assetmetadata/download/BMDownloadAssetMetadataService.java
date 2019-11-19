package MediaPoolMalBridge.service.BrandMaker.assetmetadata.download;

import MediaPoolMalBridge.clients.BrandMaker.assetgetmediadetails.client.BMDownloadMediaDetailsClient;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringBMConnectionAssetStatus;
import MediaPoolMalBridge.persistence.entity.BM.BMAssetEntity;
import MediaPoolMalBridge.service.BrandMaker.AbstractBMService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BMDownloadAssetMetadataService extends AbstractBMService {

    private final BMDownloadMediaDetailsClient downloadMediaDetailsClient;

    private TaskExecutorWrapper taskExecutorWrapper;

    public BMDownloadAssetMetadataService( final BMDownloadMediaDetailsClient downloadMediaDetailsClient,
                                           final TaskExecutorWrapper taskExecutorWrapper )
    {
        this.downloadMediaDetailsClient = downloadMediaDetailsClient;
        this.taskExecutorWrapper = taskExecutorWrapper;
    }

    public void download()
    {
        final List<BMAssetEntity> bmAssetEntities = bmAssetRepository.findMetadataUploadedOrMetadataDownloading(TransferringBMConnectionAssetStatus.METADATA_UPLOADED, TransferringBMConnectionAssetStatus.METADATA_DOWNLOADING);
        bmAssetEntities.forEach(bmAsset -> {
            taskExecutorWrapper.getTaskExecutor().execute(() -> downloadMediaDetailsClient.download(bmAsset));
        });
    }
}
