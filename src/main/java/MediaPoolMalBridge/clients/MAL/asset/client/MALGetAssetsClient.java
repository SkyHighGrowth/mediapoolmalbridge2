package MediaPoolMalBridge.clients.MAL.asset.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetAssetsClient extends MALSingleResponseClient<MALGetAssetsRequest, MALGetAssetsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetAssetsClient.class);

    private final String urlSegmest = "list_assets.json";

    public MALGetAssetsClient() {
        super( MALGetAssetsResponse.class );
    }

    public RestResponse<MALGetAssetsResponse> download(final MALGetAssetsRequest request) {
        final MultiValueMap<String, String> params = request.trnasformToGetParams();
        logger.debug("GET ASSET REQUEST {}", params);
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
