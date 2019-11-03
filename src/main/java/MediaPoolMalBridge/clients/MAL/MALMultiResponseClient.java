package MediaPoolMalBridge.clients.MAL;

import MediaPoolMalBridge.clients.MAL.transformer.ResponseTransformer;
import MediaPoolMalBridge.clients.rest.RestResponse;
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

    private static Logger logger = LoggerFactory.getLogger(MALMultiResponseClient.class);

    private String hostName = "http://api.starwoodassetlibrary.com/";

    private String malLogin = "BrandMaker";

    private String malAPIKey = "d3466104e06febcb4b706a1909aa6da6";

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
            logger.error("REST RESPONSE {}", (new Gson()).toJson(response));
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
        return (new Gson()).toJson(request);
    }

    private URI createURL(final String urlSegment) {
        final MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.set("login", malLogin);
        queryParameters.set("api_key", malAPIKey);
        queryParameters.set("per_page", "20000");
        return UriComponentsBuilder.fromHttpUrl(hostName + urlSegment)
                .queryParams(queryParameters)
                .build()
                .encode()
                .toUri();
    }

}
