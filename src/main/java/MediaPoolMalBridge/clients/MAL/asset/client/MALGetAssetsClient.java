package MediaPoolMalBridge.clients.MAL.asset.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsRequest;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetAssetsClient extends MALSingleResponseClient<Object, MALGetAssetsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetAssetsClient.class);

    private String urlSegmest = "list_assets.json";

    public MALGetAssetsClient() {

    }

    public RestResponse<MALGetAssetsResponse> download(final MALGetAssetsRequest request ) {
        final MultiValueMap<String, String> params = request.trnasformToGetParams();
        logger.error( "GET ASSET REQUEST {}", (new Gson()).toJson( params ) );
        return exchange(urlSegmest, null, HttpMethod.GET, params );
    }
}
