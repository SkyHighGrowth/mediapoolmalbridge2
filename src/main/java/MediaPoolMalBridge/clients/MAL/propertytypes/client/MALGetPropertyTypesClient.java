package MediaPoolMalBridge.clients.MAL.propertytypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.model.MALGetPropertyTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_property_types.json
 */
@Component
public class MALGetPropertyTypesClient extends MALSingleResponseClient<Object, MALGetPropertyTypesResponse> {

    private static final String urlSegment = "list_property_types.json";

    public MALGetPropertyTypesClient() {
        super(MALGetPropertyTypesResponse.class);
    }

    public RestResponse<MALGetPropertyTypesResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
