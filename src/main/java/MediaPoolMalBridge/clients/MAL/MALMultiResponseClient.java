package MediaPoolMalBridge.clients.MAL;

import MediaPoolMalBridge.clients.MAL.multiresponse.transformer.ResponseTransformer;
import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.config.AppConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class MALMultiResponseClient<REQUEST, RESPONSE> {

    private static final Gson GSON = new Gson();

    private static Logger logger = LoggerFactory.getLogger(MALMultiResponseClient.class);

    private AppConfig appConfig;

    private RestTemplate restTemplate;

    private ResponseTransformer<RESPONSE> transformer;

    protected MALMultiResponseClient(final RestTemplate restTemplate, final ResponseTransformer<RESPONSE> transformer) {
        this.restTemplate = restTemplate;
        this.transformer = transformer;
    }

    protected RestResponse<RESPONSE> exchange(final String urlSegment, final REQUEST request, final HttpMethod httpMetod) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> responseHttpEntity = new HttpEntity<>(serializeRequestBody(request), httpHeaders);
        try {
            final ResponseEntity<String> response = restTemplate.exchange(createURL(urlSegment), httpMetod, responseHttpEntity, String.class);
            logger.debug("REST RESPONSE {}", response);
            return transformer.transform(response);
        } catch (final Exception e) {
            logger.error("Can perform rest request", e);
            return new RestResponse<>(e.getMessage());
        }
    }

    private String serializeRequestBody(final REQUEST request) {
        if (request == null) {
            return null;
        }
        return GSON.toJson(request);
    }

    private URI createURL(final String urlSegment) {
        final MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.set("login", appConfig.getMalLogin());
        queryParameters.set("api_key", appConfig.getMalApiKey());
        queryParameters.set("per_page", "20000");
        return UriComponentsBuilder.fromHttpUrl(appConfig.getMalHostname() + urlSegment)
                .queryParams(queryParameters)
                .build()
                .encode()
                .toUri();
    }
}
