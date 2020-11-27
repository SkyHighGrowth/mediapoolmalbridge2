package com.brandmaker.mediapoolmalbridge.clients.mal.colors.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.colors.client.model.MALGetColorsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point list_colors.json
 */
@Component
public class MALGetColorsClient extends MALSingleResponseClient<Object, MALGetColorsResponse> {

    private static final String URL_SEGMENT = "list_colors.json";

    public MALGetColorsClient() {
        super(MALGetColorsResponse.class);
    }

    public RestResponse<MALGetColorsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
