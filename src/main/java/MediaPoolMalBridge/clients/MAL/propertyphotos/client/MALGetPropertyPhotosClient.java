package MediaPoolMalBridge.clients.MAL.propertyphotos.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point /list_available_property_photos.json
 */
@Component
public class MALGetPropertyPhotosClient extends MALSingleResponseClient<MALGetPropertyPhotosRequest, MALGetPropertyPhotosResponse> {

    private static final String urlSegment = "list_available_property_photos.json";

    public MALGetPropertyPhotosClient() {
        super(MALGetPropertyPhotosResponse.class);
    }

    public RestResponse<MALGetPropertyPhotosResponse> download(final MALGetPropertyPhotosRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(urlSegment, null, HttpMethod.GET, params);
    }
}
