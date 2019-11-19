package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download;

import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.service.MAL.AbstractMALService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MALFireDownloadPhotoService extends AbstractMALService {

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService;

    public MALFireDownloadPhotoService(final TaskExecutorWrapper taskExecutorWrapper,
                                       final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService) {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadPropertiesPhotoService = malDownloadPropertiesPhotoService;
    }

    public void downloadPhotos() {

        final List<MALAssetEntity> malAssetEntities = malAssetRepository.findByTransferringAssetStatus( TransferringAssetStatus.MAL_PHOTO_UPDATED);
        malAssetEntities.forEach(malAssetEntity ->
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> malDownloadPropertiesPhotoService.downloadMALAsset(malAssetEntity)));
    }
}
