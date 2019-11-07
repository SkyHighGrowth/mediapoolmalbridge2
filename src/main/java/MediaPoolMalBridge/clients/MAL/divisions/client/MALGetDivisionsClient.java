package MediaPoolMalBridge.clients.MAL.divisions.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.divisions.client.model.MALGetDivisionsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetDivisionsClient extends MALSingleResponseClient<Object, MALGetDivisionsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetDivisionsClient.class);

    private String urlSegmest = "list_divisions.json";

    public MALGetDivisionsClient() {

    }

    public RestResponse<MALGetDivisionsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
