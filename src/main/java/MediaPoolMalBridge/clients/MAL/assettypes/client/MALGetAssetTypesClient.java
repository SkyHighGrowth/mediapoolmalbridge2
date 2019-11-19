package MediaPoolMalBridge.clients.MAL.assettypes.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.assettypes.client.model.MALGetAssetTypesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetAssetTypesClient extends MALSingleResponseClient<Object, MALGetAssetTypesResponse> {

    private final static String urlSegment = "list_asset_types.json";

    public MALGetAssetTypesClient() {
        super(MALGetAssetTypesResponse.class);
    }

    public RestResponse<MALGetAssetTypesResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}