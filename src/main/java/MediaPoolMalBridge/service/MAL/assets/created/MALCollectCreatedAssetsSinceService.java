package MediaPoolMalBridge.service.MAL.assets.created;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectCreatedAssetsSinceService extends AbstractMALAssetsUniqueService {

    @Override
    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setDateCreatedStart(getSince());
        request.setPerPage(2000);
        downloadAssets(request);
    }
}
