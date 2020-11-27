package com.brandmaker.mediapoolmalbridge.clients.mal.collections.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.collections.client.model.MALGetCollectionsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps call to MAL server list_collections.json
 */
@Component
public class MALGetCollectionsClient extends MALSingleResponseClient<Object, MALGetCollectionsResponse> {

    private static final String URL_SEGMENT = "list_collections.json";

    public MALGetCollectionsClient() {
        super(MALGetCollectionsResponse.class);
    }

    public RestResponse<MALGetCollectionsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
