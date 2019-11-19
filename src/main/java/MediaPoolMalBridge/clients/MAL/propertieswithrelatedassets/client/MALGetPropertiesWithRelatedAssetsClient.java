package MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.model.MALGetPropertiesWithRelatedAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetPropertiesWithRelatedAssetsClient extends MALSingleResponseClient<Object, MALGetPropertiesWithRelatedAssetsResponse> {

    private static final String urlSegment = "list_properties_with_related_assets.json";

    public MALGetPropertiesWithRelatedAssetsClient() {
        super(MALGetPropertiesWithRelatedAssetsResponse.class);
    }

    public RestResponse<MALGetPropertiesWithRelatedAssetsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
