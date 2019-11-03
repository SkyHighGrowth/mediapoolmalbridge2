package MediaPoolMalBridge.clients.MAL.brands.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.brands.client.model.MALGetBrandsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetBrandsClient extends MALSingleResponseClient<Object, MALGetBrandsResponse> {

    private String urlSegmest = "list_brands.json";

    public MALGetBrandsClient() {

    }

    public RestResponse<MALGetBrandsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
