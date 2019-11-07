package MediaPoolMalBridge.clients.MAL.state.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.country.client.MALGetCountryClient;
import MediaPoolMalBridge.clients.MAL.state.client.model.MALGetStatesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetStatesClient extends MALSingleResponseClient<Object, MALGetStatesResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetCountryClient.class);

    private String urlSegmest = "list_states.json";

    public MALGetStatesClient() {

    }

    public RestResponse<MALGetStatesResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET );
    }
}
