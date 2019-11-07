package MediaPoolMalBridge.clients.MAL.propertytypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotomodified.client.MALGetModifiedPropertyPhotoClient;
import MediaPoolMalBridge.clients.MAL.propertytypes.client.model.MALGetPropertyTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetPropertyTypesClient extends MALSingleResponseClient<Object, MALGetPropertyTypesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetModifiedPropertyPhotoClient.class);

    private String urlSegmest = "list_property_types.json";

    public MALGetPropertyTypesClient() {

    }

    public RestResponse<MALGetPropertyTypesResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
