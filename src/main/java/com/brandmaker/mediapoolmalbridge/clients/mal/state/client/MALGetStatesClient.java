package com.brandmaker.mediapoolmalbridge.clients.mal.state.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.state.client.model.MALGetStatesResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_states.json
 */
@Component
public class MALGetStatesClient extends MALSingleResponseClient<Object, MALGetStatesResponse> {

    private static final String URL_SEGMENT = "list_states.json";

    public MALGetStatesClient() {
        super(MALGetStatesResponse.class);
    }

    public RestResponse<MALGetStatesResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
