package MediaPoolMalBridge.clients.MAL.properties.client;

import MediaPoolMalBridge.clients.MAL.MALSingleResponseClient;
import MediaPoolMalBridge.clients.MAL.properties.client.model.MALGetPropertiesResponse;
import MediaPoolMalBridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class MALGetPropertiesClient extends MALSingleResponseClient<Object, MALGetPropertiesResponse> {

    private String urlSegment = "list_properties.json";

    public MALGetPropertiesClient() {

    }

    public RestResponse<MALGetPropertiesResponse> download(final String pageSize) {
        final MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.set("page_size", pageSize);
        return exchange(urlSegment, null, HttpMethod.GET, queryParameters);
    }
}
