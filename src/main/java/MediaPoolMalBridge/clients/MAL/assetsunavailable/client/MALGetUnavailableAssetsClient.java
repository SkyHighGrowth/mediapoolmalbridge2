package MediaPoolMalBridge.clients.MAL.assetsunavailable.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import MediaPoolMalBridge.clients.MAL.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetUnavailableAssetsClient extends MALSingleResponseClient<Object, MALGetUnavailableAssetsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetUnavailableAssetsClient.class);

    private String urlSegmest = "list_unavailable_assets.json";

    public MALGetUnavailableAssetsClient() {

    }

    public RestResponse<MALGetUnavailableAssetsResponse> download(final MALGetUnavailableAssetsRequest request ) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.error( "GET REQUEST UNAVAILABLE PARAMS {}", (new Gson()).toJson(params) );
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
