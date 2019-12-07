package MediaPoolMalBridge.clients.MAL;

import MediaPoolMalBridge.clients.rest.RestResponse;
import MediaPoolMalBridge.persistence.entity.Bridge.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

/**
 * Class that holds common fields and method for the MAL server clients
 * @param <REQUEST> - client request
 * @param <RESPONSE> - client response
 */
public abstract class MALSingleResponseClient<REQUEST, RESPONSE> extends MALClient {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Class<RESPONSE> responseType;

    protected MALSingleResponseClient(final Class<RESPONSE> responseType) {
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
        logger.error( "REQUEST url {} headers {}", url, GSON.toJson( requestEntity) );//delete
        try {
            final ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
            logger.debug("REST RESPONSE {}", response);
            final RESPONSE fromJson = GSON.fromJson(response.getBody(), responseType);
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), fromJson);
        } catch (final Exception e) {
            final String message = String.format("While requesting from url [%s], with http method [%s], with http entity [%s], exception occured with message [%s]", httpMethod.name(), url.toString(), GSON.toJson(requestEntity), e.getMessage());
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.NONE, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message, e);
            return new RestResponse<>(e.getMessage());
        }
    }
}
