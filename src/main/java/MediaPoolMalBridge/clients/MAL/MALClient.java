package MediaPoolMalBridge.clients.MAL;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;

public abstract class MALClient<REQUEST> {

    protected static final Gson GSON = new Gson();

    private static Logger logger = LoggerFactory.getLogger(MALClient.class);

    @Value( "${mal.hostName:https://api.starwoodassetlibrary.com/}" )
    private String hostName;

    @Value( "${mal.login:robert.scholten@brandmaker.com}")
    private String malLogin;

    @Value( "${mal.apiKey:4ee54a12d9e6f1d4a535248142856a3e}")
    private String malAPIKey;

    @Autowired
    protected RestTemplate restTemplate;

    public MALClient() { }

    protected <REQUEST> String serializeRequestBody(final REQUEST request) {
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
}
