package MediaPoolMalBridge.clients.MAL.brands.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetBrandsClient extends MALSingleResponseClient<Object, MALGetBrandsResponse> {

    private final String urlSegmest = "list_brands.json";

    public MALGetBrandsClient() {
        super( MALGetBrandsResponse.class );
    }

    public RestResponse<MALGetBrandsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
