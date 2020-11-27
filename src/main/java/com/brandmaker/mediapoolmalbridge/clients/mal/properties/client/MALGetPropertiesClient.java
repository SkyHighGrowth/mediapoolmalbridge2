package com.brandmaker.mediapoolmalbridge.clients.mal.properties.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model.MALGetPropertiesRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.properties.client.model.MALGetPropertiesResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point /list_properties.json
 */
@Component
public class MALGetPropertiesClient extends MALSingleResponseClient<MALGetPropertiesRequest, MALGetPropertiesResponse> {

    private static final String URL_SEGMENT = "list_properties.json";

    public MALGetPropertiesClient() {
        super(MALGetPropertiesResponse.class);
    }

    public RestResponse<MALGetPropertiesResponse> download(final MALGetPropertiesRequest request) {
        final MultiValueMap<String, String> queryParameters = request.transformToGetParams();
        return exchange(URL_SEGMENT, request, HttpMethod.GET, queryParameters);
    }
}
