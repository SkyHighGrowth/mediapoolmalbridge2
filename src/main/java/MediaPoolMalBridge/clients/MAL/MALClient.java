package MediaPoolMalBridge.clients.MAL;

import MediaPoolMalBridge.config.AppConfig;
import MediaPoolMalBridge.persistence.repository.Bridge.ReportsRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class MALClient {

    protected static final Gson GSON = new Gson();

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    protected RestTemplate restTemplate;

    protected <REQUEST> String serializeRequestBody(final REQUEST request) {
        if (request == null) {
            return null;
        }
        return GSON.toJson(request);
    }

    protected URI createURL(final String urlSegment, final MultiValueMap<String, String> queryParameters) {
        queryParameters.set("login", appConfig.getMalLogin());
        queryParameters.set("api_key", appConfig.getMalApiKey());
        return UriComponentsBuilder.fromHttpUrl(appConfig.getMalHostname() + urlSegment)
                .queryParams(queryParameters)
                .build()
                .encode()
                .toUri();
    }
}
