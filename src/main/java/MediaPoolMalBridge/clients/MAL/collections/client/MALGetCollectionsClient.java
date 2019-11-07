package MediaPoolMalBridge.clients.MAL.collections.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.collections.client.model.MALGetCollectionsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
public class MALGetCollectionsClient extends MALSingleResponseClient<Object, MALGetCollectionsResponse> {

    private String urlSegmest = "list_collections.json";

    public MALGetCollectionsClient() {

    }

    public RestResponse<MALGetCollectionsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
