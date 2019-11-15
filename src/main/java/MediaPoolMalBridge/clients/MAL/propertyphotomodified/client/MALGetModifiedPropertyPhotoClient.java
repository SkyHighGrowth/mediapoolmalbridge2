package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetModifiedPropertyPhotoClient extends MALSingleResponseClient<MALGetModifiedPropertyPhotoRequest, MALGetModifiedPropertyPhotoResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetModifiedPropertyPhotoClient.class);

    private final String urlSegmest = "list_modified_property_photos.json";

    public MALGetModifiedPropertyPhotoClient() {
        super(MALGetModifiedPropertyPhotoResponse.class);
    }

    public RestResponse<MALGetModifiedPropertyPhotoResponse> download(final MALGetModifiedPropertyPhotoRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.debug("GET MODIFIED PROPERTY PHOTO REQUEST {}", params);
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
