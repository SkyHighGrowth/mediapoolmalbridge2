package MediaPoolMalBridge.service.MAL.assets.deleted;

import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.MALGetUnavailableAssetsClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.model.MAL.MALAssetMap;
import MediaPoolMalBridge.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class MALCollectDeletedAssetsSinceService extends AbstractService {

    private final MALGetUnavailableAssetsClient getUnavailableAssetsClient;

    private final MALAssetMap malAssetMap;

    public MALCollectDeletedAssetsSinceService(final MALGetUnavailableAssetsClient getUnavailableAssetsClient,
                                               final MALAssetMap malAssetMap)
    {
        this.getUnavailableAssetsClient = getUnavailableAssetsClient;
        this.malAssetMap = malAssetMap;
    }

    public void downloadUnavailableAssets( final String unavailableSince )
    {
        final MALGetUnavailableAssetsRequest request = new MALGetUnavailableAssetsRequest();
        request.setUnavailableSince( unavailableSince );

        final RestResponse<MALGetUnavailableAssetsResponse> response = getUnavailableAssetsClient.download( request );

        if( !response.isSuccess() ||
            response.getResponse() == null ||
            response.getResponse().getAssets() == null )
        {
            logger.error( "Can not download list of unavailable assets since {} with response {}",
                    unavailableSince,
                    GSON.toJson(response.getResponse()) );
            return;
        }

        response.getResponse()
                .getAssets()
                .forEach( malAssetMap::updateWithMALGetUnavailableAsset );
    }
}
