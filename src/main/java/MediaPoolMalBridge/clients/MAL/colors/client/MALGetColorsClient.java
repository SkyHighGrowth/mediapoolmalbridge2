package MediaPoolMalBridge.clients.MAL.colors.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.colors.client.model.MALGetColorsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_colors.json
 */
@Component
public class MALGetColorsClient extends MALSingleResponseClient<Object, MALGetColorsResponse> {

    private static final String urlSegment = "list_colors.json";

    public MALGetColorsClient() {
        super(MALGetColorsResponse.class);
    }

    public RestResponse<MALGetColorsResponse> download() {
        return exchange(urlSegment, null, HttpMethod.GET);
    }
}
