package MediaPoolMalBridge.clients.MAL.assetsunavailable.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetUnavailableAssetsClient extends MALSingleResponseClient<MALGetUnavailableAssetsRequest, MALGetUnavailableAssetsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetUnavailableAssetsClient.class);

    private final String urlSegmest = "list_unavailable_assets.json";

    public MALGetUnavailableAssetsClient() {
        super( MALGetUnavailableAssetsResponse.class );
    }

    public RestResponse<MALGetUnavailableAssetsResponse> download(final MALGetUnavailableAssetsRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.debug("GET REQUEST UNAVAILABLE PARAMS {}",params);
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
