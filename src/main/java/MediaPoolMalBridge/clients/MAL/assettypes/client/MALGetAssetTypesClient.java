package MediaPoolMalBridge.clients.MAL.assettypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.assettypes.client.model.MALGetAssetTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MALGetAssetTypesClient extends MALSingleResponseClient<Object, MALGetAssetTypesResponse> {

    private String urlSegmest = "list_asset_types.json";

    public MALGetAssetTypesClient() {

    }

    public RestResponse<MALGetAssetTypesResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}