package com.brandmaker.mediapoolmalbridge.service.mal.assets.created;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetNewBrandAssetsRequest;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.AbstractMALAssetsUniqueThreadService;
import org.springframework.stereotype.Service;

/**
 * Service to obtain new brand {@link AssetEntity} from the MAL server
 */
@Service
public class MALCollectNewBrandAssetsUniqueThreadService extends AbstractMALAssetsUniqueThreadService {
    @Override
    protected void run() {
        final MALGetNewBrandAssetsRequest request = new MALGetNewBrandAssetsRequest();
        request.setLimit(appConfig.getAppConfigData().getMalPageSize());
        downloadNewAssets(request);
    }
}
