package MediaPoolMalBridge.clients.MAL.destionations.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.destionations.client.model.MALGetDestinationsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetDestinationsClient extends MALSingleResponseClient<Object, MALGetDestinationsResponse> {

    private String urlSegmest = "list_destinations.json";

    public MALGetDestinationsClient() {

    }

    public RestResponse<MALGetDestinationsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}