package MediaPoolMalBridge.clients.MAL.state.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.state.client.model.MALGetStatesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_states.json
 */
@Component
public class MALGetStatesClient extends MALSingleResponseClient<Object, MALGetStatesResponse> {

    private static final String urlSegment = "list_states.json";

    public MALGetStatesClient() {
        super(MALGetStatesResponse.class);
    }

    public RestResponse<MALGetStatesResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
