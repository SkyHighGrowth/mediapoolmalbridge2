package com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model.MALGetUnavailabelPropertiesResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertiesunavailable.client.model.MALGetUnavailablePropertiesRequest;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point list_unavailable_properties.json
 */
@Component
public class MALGetUnavailablePropertiesClient extends MALSingleResponseClient<MALGetUnavailablePropertiesRequest, MALGetUnavailabelPropertiesResponse> {

    private static final String URL_SEGMENT = "list_unavailable_properties.json";

    public MALGetUnavailablePropertiesClient() {
        super(MALGetUnavailabelPropertiesResponse.class);
    }

    public RestResponse<MALGetUnavailabelPropertiesResponse> download(final MALGetUnavailablePropertiesRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(URL_SEGMENT, request, HttpMethod.GET, params);
    }
}
