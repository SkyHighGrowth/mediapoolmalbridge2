package com.brandmaker.mediapoolmalbridge.clients.mal;

import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import com.brandmaker.mediapoolmalbridge.persistence.entity.bridge.ReportsEntity;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportTo;
import com.brandmaker.mediapoolmalbridge.persistence.entity.enums.ReportType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

/**
 * Class that holds common fields and method for the MAL server clients
 * @param <A> - client request
 * @param <B> - client response
 */
public abstract class MALSingleResponseClient<A, B> extends MALClient {

    private final Class<B> responseType;

    protected MALSingleResponseClient(final Class<B> responseType) {
        this.responseType = responseType;
    }

    protected RestResponse<B> exchange(final String urlSegment, final A request, final HttpMethod httpMetod) {
        return exchange(urlSegment, request, httpMetod, new LinkedMultiValueMap<>());
    }

    protected RestResponse<B> exchange(final String urlSegment, final A request, final HttpMethod httpMetod, final MultiValueMap<String, String> queryParameters) {
        final URI url = createURL(urlSegment, queryParameters);
        return exchange(url, request, httpMetod);
    }

    protected RestResponse<B> exchange(final URI url, final A request, final HttpMethod httpMethod) {
        final HttpEntity<String> requestEntity = new HttpEntity<>(serializeRequestBody(request), new HttpHeaders());
        try {
            final ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
            logger.debug("REST RESPONSE {}", response);
            final B fromJson = GSON.fromJson(response.getBody(), responseType);
            return new RestResponse<>(response.getStatusCode(), response.getHeaders(), fromJson);
        } catch (final Exception e) {
            final String message = String.format("While requesting from url [%s], with http method [%s], with http entity [%s], exception occured with message [%s]", httpMethod.name(), url.toString(), GSON.toJson(requestEntity), e.getMessage());
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), null, message, ReportTo.NONE, null, null, null );
            reportsRepository.save( reportsEntity );
            logger.error(message, e);
            return new RestResponse<>(e.getMessage());
        }
    }
}
