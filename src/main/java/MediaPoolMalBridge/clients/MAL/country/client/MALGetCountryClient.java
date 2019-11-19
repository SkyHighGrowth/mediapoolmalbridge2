package MediaPoolMalBridge.clients.MAL.country.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.country.client.model.MALGetCountryResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetCountryClient extends MALSingleResponseClient<Object, MALGetCountryResponse> {

    private static final String urlSegment = "list_countries.json";

    public MALGetCountryClient() {
        super(MALGetCountryResponse.class);
    }

    public RestResponse<MALGetCountryResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
