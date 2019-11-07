package MediaPoolMalBridge.clients.MAL.kits.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.kits.client.model.MALGetKitsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetKitsClient extends MALSingleResponseClient<Object, MALGetKitsResponse> {

    private String urlSegmest = "list_kits.json";

    public MALGetKitsClient() {

    }

    public RestResponse<MALGetKitsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
