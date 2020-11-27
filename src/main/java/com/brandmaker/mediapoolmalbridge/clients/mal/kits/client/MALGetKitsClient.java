package com.brandmaker.mediapoolmalbridge.clients.mal.kits.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.kits.client.model.MALGetKitsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_kits.json
 */
@Component
public class MALGetKitsClient extends MALSingleResponseClient<Object, MALGetKitsResponse> {

    private static final String URL_SEGMENT = "list_kits.json";

    public MALGetKitsClient() {
        super(MALGetKitsResponse.class);
    }

    public RestResponse<MALGetKitsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
