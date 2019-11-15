package MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.propertieswithrelatedassets.client.model.MALGetPropertiesWithRelatedAssetsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetPropertiesWithRelatedAssetsClient extends MALSingleResponseClient<Object, MALGetPropertiesWithRelatedAssetsResponse> {

    private static Logger logger = LoggerFactory.getLogger(MALGetPropertiesWithRelatedAssetsClient.class);

    private final String urlSegmest = "list_properties_with_related_assets.json";

    public MALGetPropertiesWithRelatedAssetsClient() {
        super(MALGetPropertiesWithRelatedAssetsResponse.class);
    }

    public RestResponse<MALGetPropertiesWithRelatedAssetsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
