package MediaPoolMalBridge.clients.MAL.properties.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesRequest;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


@Component
public class MALGetPropertiesClient extends MALSingleResponseClient<MALGetPropertiesRequest, MALGetPropertiesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetPropertiesClient.class);

    private final String urlSegment = "list_properties.json";

    public MALGetPropertiesClient() {
        super( MALGetPropertiesResponse.class );
    }

    public RestResponse<MALGetPropertiesResponse> download(final MALGetPropertiesRequest request) {
        final MultiValueMap<String, String> queryParameters = request.transformToGetParams();
        logger.debug("GET PROPERTIES REQUEST {}", queryParameters);
        return exchange(urlSegment, null, HttpMethod.GET, queryParameters);
    }
}
