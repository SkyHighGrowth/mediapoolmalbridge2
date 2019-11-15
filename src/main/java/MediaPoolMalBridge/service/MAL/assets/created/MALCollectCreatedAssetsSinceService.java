package MediaPoolMalBridge.service.MAL.assets.created;

import MediaPoolMalBridge.clients.MAL.asset.client.MALGetAssetsClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MALCollectCreatedAssetsSinceService {

    private static Logger logger = LoggerFactory.getLogger(MALCollectCreatedAssetsSinceService.class);

    private static final Gson GSON = new Gson();

    private final MALGetAssetsClient getAssetsClient;

    private final MALAssetMap malAssetMap;

    public MALCollectCreatedAssetsSinceService(final MALGetAssetsClient malGetAssetsClient,
                                               final MALAssetMap malAssetMap )
    {
        this.getAssetsClient = malGetAssetsClient;
        this.malAssetMap  = malAssetMap;
    }

    public void downloadCreatedAssets( final String createdSince )
    {
        final MALGetAssetsRequest request = new MALGetAssetsRequest();
        request.setDateCreatedStart(createdSince);
        request.setPerPage(200);
        request.setPage(1);
        RestResponse<MALGetAssetsResponse> response = getAssetsClient.download(request);
        if (!response.isSuccess() ||
                response.getResponse() == null ||
                StringUtils.isBlank(response.getResponse().getTotalPages()) ||
                response.getResponse().getAssets() == null) {
            logger.error("Can not download list of created asset since {}, with httpStatus {}, and responseBody {}",
                    createdSince,
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
            logger.error( "Can not parse totalPages of created asset since {}, with httpStatus {}, and responseBody {}",
                    createdSince,
                    response.getHttpStatus(),
                    GSON.toJson(response.getResponse()));
            return;
        }

        for (int page = 0; page < totalPages; ++page) {
            request.setDateCreatedStart(createdSince);
            request.setPerPage(200);
            request.setPage(page + 1);
            response = getAssetsClient.download(request);
            if (!response.isSuccess() ||
                    response.getResponse() == null ||
                    response.getResponse().getAssets() == null) {
                logger.error("Can not download page {} of created asset since {}, with httpStatus {}, and responseBody {}",
                        page + 1,
                        createdSince,
                        response.getHttpStatus(),
                        GSON.toJson(response.getResponse()));
                continue;
            }
            response.getResponse()
                    .getAssets()
                    .forEach( malAssetMap::updateWithMALGetAssetCreated );
        }
    }
}
