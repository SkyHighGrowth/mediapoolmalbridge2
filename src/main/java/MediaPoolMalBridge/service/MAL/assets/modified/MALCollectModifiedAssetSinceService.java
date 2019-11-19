package MediaPoolMalBridge.service.MAL.assets.modified;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedAssetSinceService extends AbstractMALAssetsService {

    public void downloadModifiedAssets(final String modifiedSince) {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setLastModifiedStart(modifiedSince);
        request.setPerPage(200);
        request.setPage(1);
        downloadAssets(request, modifiedSince);
    }
}
