package MediaPoolMalBridge.clients.MAL;

import MediaPoolMalBridge.clients.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

public abstract class MALSingleResponseClient<REQUEST, RESPONSE> extends MALClient {

    private static Logger logger = LoggerFactory.getLogger(MALSingleResponseClient.class);

    private final Class<RESPONSE> responseType;

    protected MALSingleResponseClient( final Class<RESPONSE> responseType) {
        this.responseType = responseType;
    }

    protected RestResponse<RESPONSE> exchange(final String urlSegment, final REQUEST request, final HttpMethod httpMetod) {
        return exchange(urlSegment, request, httpMetod, new LinkedMultiValueMap<>());
    }

    protected RestResponse<RESPONSE> exchange(final String urlSegment, final REQUEST request, final HttpMethod httpMetod, final MultiValueMap<String, String> queryParameters) {
        final URI url = createURL(urlSegment, queryParameters);
        return exchange(url, request, httpMetod);
    }

    protected RestResponse<RESPONSE> exchange(final URI url, final REQUEST request, final HttpMethod httpMethod) {
        final HttpEntity<String> requestEntity = new HttpEntity<>(serializeRequestBody(request), new HttpHeaders());
        try {
            final ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
            logger.debug("REST RESPONSE {}", response);
            final RESPONSE fromJson = GSON.fromJson( response.getBody(), responseType );
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), fromJson);
        } catch (final Exception e) {
            logger.error("Can not perform rest request", e);
            return new RestResponse<>(e.getMessage());
        }
    }
}
