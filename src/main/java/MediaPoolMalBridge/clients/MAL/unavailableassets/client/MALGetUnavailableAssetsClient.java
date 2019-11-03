package MediaPoolMalBridge.clients.MAL.unavailableassets.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.unavailableassets.client.model.MALGetUnavailableAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetUnavailableAssetsClient extends MALSingleResponseClient<Object, MALGetUnavailableAssetsResponse> {

    private String urlSegmest = "list_unavailable_assets.json";

    public MALGetUnavailableAssetsClient() {

    }

    public RestResponse<MALGetUnavailableAssetsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
