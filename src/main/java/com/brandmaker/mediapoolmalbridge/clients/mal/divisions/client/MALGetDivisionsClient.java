package com.brandmaker.mediapoolmalbridge.clients.mal.divisions.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.divisions.client.model.MALGetDivisionsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_divisions.json
 */
@Component
public class MALGetDivisionsClient extends MALSingleResponseClient<Object, MALGetDivisionsResponse> {

    private static final String URL_SEGMENT = "list_divisions.json";

    public MALGetDivisionsClient() {
        super(MALGetDivisionsResponse.class);
    }

    public RestResponse<MALGetDivisionsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
