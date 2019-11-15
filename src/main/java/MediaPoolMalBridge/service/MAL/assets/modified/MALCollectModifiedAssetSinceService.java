package MediaPoolMalBridge.service.MAL.assets.modified;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.service.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MALCollectModifiedAssetSinceService extends AbstractService {

    private final MALGetAssetsClient getAssetsClient;

    private final MALAssetMap malAssetMap;

    public MALCollectModifiedAssetSinceService( final MALGetAssetsClient malGetAssetsClient,
                                                final MALAssetMap malAssetMap )
    {
        this.getAssetsClient = malGetAssetsClient;
        this.malAssetMap = malAssetMap;
    }

    public void downloadModifiedAssets( final String  modifiedSince )
    {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setLastModifiedStart(modifiedSince);
        request.setPerPage(200);
        request.setPage(1);
        RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages()) ||
                response.getResponse().getAssets() == null) {
            logger.error("Can not download list of modified asset since {}, with httpStatus {}, and responseBody {}",
                    modifiedSince,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            return;
        }

        final int totalPages;
        try {
            totalPages = Integer.parseInt(response.getResponse().getTotalPages());
        }
        catch( final Exception e )
        {
            logger.error( "Can not parse totalPages of modified asset since {}, with httpStatus {}, and responseBody {}",
                    modifiedSince,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            return;
        }

        for (int page = 0; page < totalPages; ++page) {
            request.setLastModifiedStart(modifiedSince);
            request.setPerPage(200);
            request.setPage(page + 1);
            response = getAssetsClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getAssets() == null) {
                logger.error("Can not download page {} of modified asset since {}, with httpStatus {}, and responseBody {}",
                        page + 1,
                        modifiedSince,
                        response.getHttpStatus(),
                        GSON.toJson(response.getResponse()));
                continue;
            }
            response.getResponse()
                    .getAssets()
                    .forEach(malAssetMap::updateWithMALGetAssetModified);
        }
    }
}
