package com.brandmaker.mediapoolmalbridge.clients.mal.country.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.country.client.model.MALGetCountryResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_countries.json
 */
@Component
public class MALGetCountryClient extends MALSingleResponseClient<Object, MALGetCountryResponse> {

    private static final String URL_SEGMENT = "list_countries.json";

    public MALGetCountryClient() {
        super(MALGetCountryResponse.class);
    }

    public RestResponse<MALGetCountryResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
