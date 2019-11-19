package MediaPoolMalBridge.service.MAL.assets.download;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MALFireDownloadAssetsService extends AbstractMALService {

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadAssetService malDownloadAssetService;

    public MALFireDownloadAssetsService(final TaskExecutorWrapper taskExecutorWrapper,
                                        final MALDownloadAssetService malDownloadAssetService) {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadAssetService = malDownloadAssetService;
    }

    public void downloadAssets() {

        final List<MALAssetEntity> malAssetEntities = malAssetRepository.findMalUpdatedOrMalCreated( TransferringAssetStatus.MAL_UPDATED, TransferringAssetStatus.MAL_CREATED );
        malAssetEntities.forEach(malAssetEntity ->
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> malDownloadAssetService.downloadMALAsset(malAssetEntity)));
    }
}
