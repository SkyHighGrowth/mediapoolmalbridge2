package MediaPoolMalBridge.clients.MAL.propertiesunavailable.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import MediaPoolMalBridge.clients.MAL.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetUnavailablePropertiesClient extends MALSingleResponseClient<MALGetUnavailablePropertiesRequest, MALGetUnavailabelPropertiesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetUnavailablePropertiesClient.class);

    private final String urlSegmest = "list_unavailable_properties.json";

    public MALGetUnavailablePropertiesClient() {
        super(MALGetUnavailabelPropertiesResponse.class);
    }

    public RestResponse<MALGetUnavailabelPropertiesResponse> download(final MALGetUnavailablePropertiesRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.debug("GET UNAVAILABLE PROPERTIES REQUEST {}", params);
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
