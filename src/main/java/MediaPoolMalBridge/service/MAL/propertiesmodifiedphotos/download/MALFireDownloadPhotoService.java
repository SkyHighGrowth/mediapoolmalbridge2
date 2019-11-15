package MediaPoolMalBridge.service.MAL.propertiesmodifiedphotos.download;

import MediaPoolMalBridge.clients.MAL.model.MALAsset;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.model.asset.TransferringAsset;
import MediaPoolMalBridge.model.asset.TransferringAssetStatus;
import MediaPoolMalBridge.service.AbstractService;
import MediaPoolMalBridge.tasks.MAL.TaskExecutorWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class MALFireDownloadPhotoService extends AbstractService {

    private final TaskExecutorWrapper taskExecutorWrapper;

    private final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService;

    private final MALAssetMap malAssetMap;

    public MALFireDownloadPhotoService(final TaskExecutorWrapper taskExecutorWrapper,
                                        final MALDownloadPropertiesPhotoService malDownloadPropertiesPhotoService,
                                        final MALAssetMap malAssetMap )
    {
        this.taskExecutorWrapper = taskExecutorWrapper;
        this.malDownloadPropertiesPhotoService = malDownloadPropertiesPhotoService;
        this.malAssetMap = malAssetMap;
    }

    public void downloadPhotos() {

        final Collection<TransferringAsset> malAssets = malAssetMap.values();

        malAssets.stream()
                .filter( Objects::nonNull )
                .filter( transferringAsset -> TransferringAssetStatus.MAL_PHOTO_UPDATED.equals( transferringAsset.getTransferringAssetStatus() ) )
                .forEach( transferringAsset -> {
                    if( transferringAsset instanceof MALAsset)
                    {
                        final MALAsset malAsset = (MALAsset) transferringAsset;
                        taskExecutorWrapper.getTaskExecutor()
                                .execute( () -> malDownloadPropertiesPhotoService.downloadMALAsset( malAsset ) );
                    } } );
    }
}
