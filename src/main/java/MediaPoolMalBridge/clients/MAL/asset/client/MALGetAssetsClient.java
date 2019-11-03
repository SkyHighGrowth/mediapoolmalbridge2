package MediaPoolMalBridge.clients.MAL.asset.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.asset.client.model.MALGetAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetAssetsClient extends MALSingleResponseClient<Object, MALGetAssetsResponse> {

    private String urlSegmest = "list_assets.json";

    public MALGetAssetsClient() {

    }

    public RestResponse<MALGetAssetsResponse> download(final String pageSize) {
        final MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.set("page_size", pageSize);
        return exchange(urlSegmest, null, HttpMethod.GET, queryParameters);
    }
}
