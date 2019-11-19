package MediaPoolMalBridge.clients.MAL.collections.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.collections.client.model.MALGetCollectionsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
public class MALGetCollectionsClient extends MALSingleResponseClient<Object, MALGetCollectionsResponse> {

    private static final String urlSegment = "list_collections.json";

    public MALGetCollectionsClient() {
        super(MALGetCollectionsResponse.class);
    }

    public RestResponse<MALGetCollectionsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
