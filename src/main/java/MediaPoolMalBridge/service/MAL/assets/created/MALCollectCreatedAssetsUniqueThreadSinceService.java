package MediaPoolMalBridge.service.MAL.assets.created;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.config.AppConfigData;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service to obtain {@link MediaPoolMalBridge.persistence.entity.MAL.MALAssetEntity} from the MAL server
 * it checks for the assets which are created {@link AppConfigData#getMalLookInThePastDays} days before today
 */
@Service
public class MALCollectCreatedAssetsUniqueThreadSinceService extends AbstractMALAssetsUniqueThreadService {

    @Override
    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setDateCreatedStart(getSince());
        request.setPerPage(2000);
        downloadAssets(request);
    }
}
