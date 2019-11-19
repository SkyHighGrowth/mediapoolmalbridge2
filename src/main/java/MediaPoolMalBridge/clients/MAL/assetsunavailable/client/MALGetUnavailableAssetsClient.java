package MediaPoolMalBridge.clients.MAL.assetsunavailable.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetUnavailableAssetsClient extends MALSingleResponseClient<MALGetUnavailableAssetsRequest, MALGetUnavailableAssetsResponse> {

    private final static String urlSegment = "list_unavailable_assets.json";

    public MALGetUnavailableAssetsClient() {
        super(MALGetUnavailableAssetsResponse.class);
    }

    public RestResponse<MALGetUnavailableAssetsResponse> download(final MALGetUnavailableAssetsRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(urlSegment, null, HttpMethod.GET, params);
    }
}
