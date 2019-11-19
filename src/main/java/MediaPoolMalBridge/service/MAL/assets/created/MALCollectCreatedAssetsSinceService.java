package MediaPoolMalBridge.service.MAL.assets.created;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectCreatedAssetsSinceService extends AbstractMALAssetsService {

    public void downloadCreatedAssets(final String createdSince) {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setDateCreatedStart(createdSince);
        request.setPerPage(200);
        downloadAssets(request, createdSince);
    }
}
