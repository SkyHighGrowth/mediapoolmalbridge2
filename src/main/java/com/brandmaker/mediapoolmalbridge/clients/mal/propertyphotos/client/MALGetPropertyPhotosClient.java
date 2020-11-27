package com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client.model.MALGetPropertyPhotosRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotos.client.model.MALGetPropertyPhotosResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point /list_available_property_photos.json
 */
@Component
public class MALGetPropertyPhotosClient extends MALSingleResponseClient<MALGetPropertyPhotosRequest, MALGetPropertyPhotosResponse> {

    private static final String URL_SEGMENT = "list_available_property_photos.json";

    public MALGetPropertyPhotosClient() {
        super(MALGetPropertyPhotosResponse.class);
    }

    public RestResponse<MALGetPropertyPhotosResponse> download(final MALGetPropertyPhotosRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(URL_SEGMENT, null, HttpMethod.GET, params);
    }
}
