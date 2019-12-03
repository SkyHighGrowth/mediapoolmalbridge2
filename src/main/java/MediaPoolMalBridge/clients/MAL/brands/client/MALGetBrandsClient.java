package MediaPoolMalBridge.clients.MAL.brands.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server list_brands.json end point
 */
@Component
public class MALGetBrandsClient extends MALSingleResponseClient<Object, MALGetBrandsResponse> {

    private static final String urlSegment = "list_brands.json";

    public MALGetBrandsClient() {
        super(MALGetBrandsResponse.class);
    }

    public RestResponse<MALGetBrandsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
