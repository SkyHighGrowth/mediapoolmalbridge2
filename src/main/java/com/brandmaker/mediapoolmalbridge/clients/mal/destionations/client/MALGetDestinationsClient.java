package com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.destionations.client.model.MALGetDestinationsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_destinations.json
 */
@Component
public class MALGetDestinationsClient extends MALSingleResponseClient<Object, MALGetDestinationsResponse> {

    private static final String URL_SEGMENT = "list_destinations.json";

    public MALGetDestinationsClient() {
        super(MALGetDestinationsResponse.class);
    }

    public RestResponse<MALGetDestinationsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}