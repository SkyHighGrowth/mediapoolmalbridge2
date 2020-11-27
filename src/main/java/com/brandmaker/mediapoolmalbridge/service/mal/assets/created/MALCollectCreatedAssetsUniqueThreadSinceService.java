package com.brandmaker.mediapoolmalbridge.service.mal.assets.created;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAssetsRequest;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.AbstractMALAssetsUniqueThreadService;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.AssetEntity;
import org.springframework.stereotype.Service;

/**
 * Service to obtain {@link AssetEntity} from the MAL server
 * it checks for the assets which are created {@link AppConfigData#getMalLookInThePastDays} days before today
 */
@Service
public class MALCollectCreatedAssetsUniqueThreadSinceService extends AbstractMALAssetsUniqueThreadService {

    @Override
    protected void run() {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        if (appConfig.intervalFilterEnable()) {
            request.setDateCreatedStart(appConfig.getFilterStartDate());
            request.setDateCreatedEnd(appConfig.getFilterEndDate());
        } else {
            request.setDateCreatedStart(getSince());
        }

        request.setPerPage(appConfig.getMalPageSize());
        downloadAssets(request);
    }
}
