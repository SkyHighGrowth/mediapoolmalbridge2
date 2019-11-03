package MediaPoolMalBridge.clients.MAL;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class MALClient<REQUEST> {

    protected static final Gson GSON = new Gson();

    private static Logger logger = LoggerFactory.getLogger(MALClient.class);

    private String hostName = "http://api.starwoodassetlibrary.com/";

    private String malLogin = "BrandMaker";

    private String malAPIKey = "d3466104e06febcb4b706a1909aa6da6";

    @Autowired
    protected RestTemplate restTemplate;

    public MALClient() { }

    protected String serializeRequestBody(final REQUEST request) {
        if (request == null) {
            return null;
        }
        return GSON.toJson(request);
    }

    protected URI createURL(final String urlSegment, final MultiValueMap<String, String> queryParameters) {
        queryParameters.set("login", malLogin);
        queryParameters.set("api_key", malAPIKey);
        return UriComponentsBuilder.fromHttpUrl(hostName + urlSegment)
                .queryParams(queryParameters)
                .build()
                .encode()
                .toUri();
    }

    protected URI createURL(final String urlSegment) {
        return createURL(urlSegment, new LinkedMultiValueMap<>());
    }
}
