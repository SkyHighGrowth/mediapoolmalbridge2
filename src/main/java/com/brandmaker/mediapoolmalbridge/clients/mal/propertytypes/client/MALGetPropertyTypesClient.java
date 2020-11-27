package com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertytypes.client.model.MALGetPropertyTypesResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server end point /list_property_types.json
 */
@Component
public class MALGetPropertyTypesClient extends MALSingleResponseClient<Object, MALGetPropertyTypesResponse> {

    private static final String URL_SEGMENT = "list_property_types.json";

    public MALGetPropertyTypesClient() {
        super(MALGetPropertyTypesResponse.class);
    }

    public RestResponse<MALGetPropertyTypesResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
