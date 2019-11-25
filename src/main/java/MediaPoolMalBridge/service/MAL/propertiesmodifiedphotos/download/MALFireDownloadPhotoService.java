package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download;

import MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity;
import MediaPoolMalBridge.persistence.entity.enums.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.MAL.AbstractMALUniqueService;
import MediaPoolMalBridge.tasks.TaskExecutorWrapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MALFireDownloadPhotoService extends AbstractMALUniqueService {

    private final int pageSize = 1000;

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService;

    public MALFireDownloadPhotoService(final TaskExecutorWrapper taskExecutorWrapper,
                                       final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService) {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadPropertiesPhotoService = malDownloadPropertiesPhotoService;
    }

    @Override
    protected void run() {
        boolean condition = true;
        for (int page = 0; condition; ++page) {
            final Slice<MALAssetEntity> malAssetEntities = malAssetRepository.findAllByTransferringAssetStatus( TransferringAssetStatus.MAL_PHOTO_UPDATED,
                    PageRequest.of(page, pageSize));
            condition = malAssetEntities.hasNext();
            malAssetEntities.forEach( malAssetEntity -> {
                while( true ) {
                    if( taskExecutorWrapper.getTaskExecutor().getThreadPoolExecutor().getQueue().size() > 9000 ) {
                        try {
                            synchronized( malDownloadPropertiesPhotoService ) {
                                malDownloadPropertiesPhotoService.wait(10000);
                            }
                        } catch ( final InterruptedException e ) {
                            throw new RuntimeException();
                        }
                    } else {
                        break;
                    }
                }
                taskExecutorWrapper.getTaskExecutor()
                        .execute(() -> malDownloadPropertiesPhotoService.start(malAssetEntity));
            } );
        }
    }
}
