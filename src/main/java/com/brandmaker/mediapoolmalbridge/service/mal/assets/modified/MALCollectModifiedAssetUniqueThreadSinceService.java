package com.brandmaker.mediapoolmalbridge.service.mal.assets.modified;

import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAssetsRequest;
import com.brandmaker.mediapoolmalbridge.service.mal.assets.AbstractMALAssetsUniqueThreadService;
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
