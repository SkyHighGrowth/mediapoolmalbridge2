package MediaPoolMalBridge.clients.MAL.propertyphotos.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertyphotos.client.model.MALGetPropertyPhotosResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetPropertyPhotosClient extends MALSingleResponseClient<Object, MALGetPropertyPhotosResponse> {

    private String urlSegmest = "list_available_property_photos.json";

    public MALGetPropertyPhotosClient() {

    }

    public RestResponse<MALGetPropertyPhotosResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
