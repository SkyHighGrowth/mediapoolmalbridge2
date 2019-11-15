package MediaPoolMalBridge.clients.MAL.colors.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.colors.client.model.MALGetColorsResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetColorsClient extends MALSingleResponseClient<Object, MALGetColorsResponse> {

    private final String urlSegmest = "list_colors.json";

    public MALGetColorsClient() {
        super( MALGetColorsResponse.class );
    }

    public RestResponse<MALGetColorsResponse> download() {
        return exchange(urlSegmest, null, HttpMethod.GET);
    }
}
