package MediaPoolMalBridge.clients.MAL.kits.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.kits.client.model.MALGetKitsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_kits.json
 */
@Component
public class MALGetKitsClient extends MALSingleResponseClient<Object, MALGetKitsResponse> {

    private static final String urlSegment = "list_kits.json";

    public MALGetKitsClient() {
        super(MALGetKitsResponse.class);
    }

    public RestResponse<MALGetKitsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
