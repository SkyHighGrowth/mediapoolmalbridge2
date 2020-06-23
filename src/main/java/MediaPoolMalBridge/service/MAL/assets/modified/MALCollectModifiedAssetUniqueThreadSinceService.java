package MediaPoolMalBridge.service.MAL.assets.modified;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service that collects modified assets from MAL server
 */
@Service
public class MALCollectModifiedAssetUniqueThreadSinceService extends AbstractMALAssetsUniqueThreadService {

    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();

        if (appConfig.intervalFilterEnable()) {
            request.setLastModifiedStart(appConfig.getFilterStartDate());
            request.setLastModifiedEnd(appConfig.getFilterEndDate());
        } else {
            request.setLastModifiedStart(getSince());
        }

        request.setPerPage(appConfig.getMalPageSize());
        request.setPage(1);
        downloadAssets(request);
    }
}
