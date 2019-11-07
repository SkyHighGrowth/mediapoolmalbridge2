package MediaPoolMalBridge.clients.MAL.propertyphotos.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetPropertyPhotosClient extends MALSingleResponseClient<Object, MALGetPropertyPhotosResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetPropertyPhotosClient.class);

    private String urlSegmest = "list_available_property_photos.json";

    public MALGetPropertyPhotosClient() {

    }

    public RestResponse<MALGetPropertyPhotosResponse> download(final MALGetPropertyPhotosRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.error( "GET PROPERTY PHOTO REQUEST {}", (new Gson()).toJson( params ) );
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
