package MediaPoolMalBridge.service.MAL.assets.modified;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedAssetSinceService extends AbstractMALAssetsUniqueService {

    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setLastModifiedStart(getSince());
        request.setPerPage(2000);
        request.setPage(1);
        downloadAssets(request);
    }
}
