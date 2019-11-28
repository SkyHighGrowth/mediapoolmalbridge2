package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download;

import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueThreadService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MALFireDownloadPhotoUniqueThreadService extends AbstractMALUniqueThreadService {

    private final static int pageSize = 2000;

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService;

    public MALFireDownloadPhotoUniqueThreadService(final TaskExecutorWrapper taskExecutorWrapper,
                                                   final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService) {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadPropertiesPhotoService = malDownloadPropertiesPhotoService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringAssetStatusAndUpdatedIsAfter(
                    TransferringAssetStatus.MAL_PHOTO_UPDATED, getTodayMidnight(), PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach( malAssetEntity -> {
                if( taskExecutorWrapper.getQueueSize() < appConfig.getThreadexecutorQueueLengthMax() ) {
                    taskExecutorWrapper.getTaskExecutor().execute(() -> malDownloadPropertiesPhotoService.start(malAssetEntity)); }
            } );
        }
    }
}
