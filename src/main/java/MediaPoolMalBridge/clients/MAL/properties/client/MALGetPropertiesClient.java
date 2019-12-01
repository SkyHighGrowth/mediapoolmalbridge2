package MediaPoolMalBridge.clients.MAL.properties.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesRequest;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetPropertiesClient extends MALSingleResponseClient<MALGetPropertiesRequest, MALGetPropertiesResponse> {

    private static final String urlSegment = "list_properties.json";

    public MALGetPropertiesClient() {
        super(MALGetPropertiesResponse.class);
    }

    public RestResponse<MALGetPropertiesResponse> download(final MALGetPropertiesRequest request) {
        final MultiValueMap<String, String> queryParameters = request.transformToGetParams();
        return exchange(urlSegment, request, HttpMethod.GET, queryParameters);
    }
}
