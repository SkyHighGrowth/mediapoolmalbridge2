package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point /list_modified_property_photos.json
 */
@Component
public class MALGetModifiedPropertyPhotoClient extends MALSingleResponseClient<MALGetModifiedPropertyPhotoRequest, MALGetModifiedPropertyPhotoResponse> {

    private static final String urlSegment = "list_modified_property_photos.json";

    public MALGetModifiedPropertyPhotoClient() {
        super(MALGetModifiedPropertyPhotoResponse.class);
    }

    public RestResponse<MALGetModifiedPropertyPhotoResponse> download(final MALGetModifiedPropertyPhotoRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(urlSegment, null, HttpMethod.GET, params);
    }
}
