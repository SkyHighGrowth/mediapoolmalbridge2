package MediaPoolMalBridge.clients.MAL.country.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.country.client.model.MALGetCountryResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetCountryClient extends MALSingleResponseClient<Object, MALGetCountryResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetCountryClient.class);

    private final String urlSegmest = "list_countries.json";

    public MALGetCountryClient() {
        super( MALGetCountryResponse.class );
    }

    public RestResponse<MALGetCountryResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
