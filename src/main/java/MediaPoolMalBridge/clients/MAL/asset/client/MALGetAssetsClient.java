package MediaPoolMalBridge.clients.MAL.asset.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to list_asset.json end point to fetch assets modification, appearence
 */
@Component
public class MALGetAssetsClient extends MALSingleResponseClient<MALGetAssetsRequest, MALGetAssetsResponse> {

    private final static String urlSegment = "list_assets.json";

    public MALGetAssetsClient() {
        super(MALGetAssetsResponse.class);
    }

    public RestResponse<MALGetAssetsResponse> download(final MALGetAssetsRequest request) {
        final MultiValueMap<String, String> params = request.trnasformToGetParams();
        return exchange(urlSegment, null, HttpMethod.GET, params);
    }
}
