package MediaPoolMalBridge.clients.MAL.divisions.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.divisions.client.model.MALGetDivisionsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_divisions.json
 */
@Component
public class MALGetDivisionsClient extends MALSingleResponseClient<Object, MALGetDivisionsResponse> {

    private static final String urlSegment = "list_divisions.json";

    public MALGetDivisionsClient() {
        super(MALGetDivisionsResponse.class);
    }

    public RestResponse<MALGetDivisionsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
