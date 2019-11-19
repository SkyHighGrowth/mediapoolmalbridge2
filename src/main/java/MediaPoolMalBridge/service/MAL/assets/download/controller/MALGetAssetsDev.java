package MediaPoolMalBridge.service.MAL.assets.download.controller;

import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.service.MAL.assets.AbstractMALAssetsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MALGetAssetsDev extends AbstractMALAssetsService {

    public void downloadModifiedAssets(final MALGetAssetsRequest request) {
        transformPagesIntoAssets(request, 1);
    }
}
