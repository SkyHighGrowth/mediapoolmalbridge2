package MediaPoolMalBridge.clients.MAL.propertiesunavailable.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetUnavailablePropertiesClient extends MALSingleResponseClient<MALGetUnavailablePropertiesRequest, MALGetUnavailabelPropertiesResponse> {

    private static final String urlSegment = "list_unavailable_properties.json";

    public MALGetUnavailablePropertiesClient() {
        super(MALGetUnavailabelPropertiesResponse.class);
    }

    public RestResponse<MALGetUnavailabelPropertiesResponse> download(final MALGetUnavailablePropertiesRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(urlSegment, null, HttpMethod.GET, params);
    }
}
