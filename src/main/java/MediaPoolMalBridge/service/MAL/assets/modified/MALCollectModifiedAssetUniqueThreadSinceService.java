package MediaPoolMalBridge.service.MAL.assets.modified;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueThreadService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedAssetUniqueThreadSinceService extends AbstractMALAssetsUniqueThreadService {

    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setLastModifiedStart(getSince());
        request.setPerPage(2000);
        request.setPage(1);
        downloadAssets(request);
    }
}
