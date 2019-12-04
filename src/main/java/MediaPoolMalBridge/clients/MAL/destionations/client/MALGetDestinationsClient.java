package MediaPoolMalBridge.clients.MAL.destionations.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.MALGetDestinationsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_destinations.json
 */
@Component
public class MALGetDestinationsClient extends MALSingleResponseClient<Object, MALGetDestinationsResponse> {

    private static final String urlSegment = "list_destinations.json";

    public MALGetDestinationsClient() {
        super(MALGetDestinationsResponse.class);
    }

    public RestResponse<MALGetDestinationsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}