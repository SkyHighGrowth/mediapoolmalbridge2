package com.brandmaker.mediapoolmalbridge.clients.mal.brands.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.brands.client.model.MALGetBrandsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server list_brands.json end point
 */
@Component
public class MALGetBrandsClient extends MALSingleResponseClient<Object, MALGetBrandsResponse> {

    private static final String URL_SEGMENT = "list_brands.json";

    public MALGetBrandsClient() {
        super(MALGetBrandsResponse.class);
    }

    public RestResponse<MALGetBrandsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
