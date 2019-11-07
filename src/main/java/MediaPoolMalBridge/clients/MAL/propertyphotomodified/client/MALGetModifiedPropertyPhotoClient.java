package MediaPoolMalBridge.clients.MAL.propertyphotomodified.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class MALGetModifiedPropertyPhotoClient  extends MALSingleResponseClient<Object, MALGetModifiedPropertyPhotoResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetModifiedPropertyPhotoClient.class);

    private String urlSegmest = "list_modified_property_photos.json";

    public MALGetModifiedPropertyPhotoClient() {

    }

    public RestResponse<MALGetModifiedPropertyPhotoResponse> download(final MALGetModifiedPropertyPhotoRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        logger.error( "GET MODIFIED PROPERTY PHOTO REQUEST {}", (new Gson()).toJson( params ) );
        return exchange(urlSegmest, null, HttpMethod.GET, params);
    }
}
