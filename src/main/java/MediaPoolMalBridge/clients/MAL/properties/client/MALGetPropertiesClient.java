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
public class MALGetPropertiesClient extends MALSingleResponseClient<Object, MALGetPropertiesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetPropertiesClient.class);

    private String urlSegment = "list_properties.json";

    public MALGetPropertiesClient() {

    }

    public RestResponse<MALGetPropertiesResponse> download( final MALGetPropertiesRequest request ) {
        final MultiValueMap<String, String> queryParameters = request.transformToGetParams();
        logger.error( "GET PROPERTIES REQUEST {}", (new Gson()).toJson( queryParameters ) );
        return exchange(urlSegment, null, HttpMethod.GET, queryParameters);
    }
}
