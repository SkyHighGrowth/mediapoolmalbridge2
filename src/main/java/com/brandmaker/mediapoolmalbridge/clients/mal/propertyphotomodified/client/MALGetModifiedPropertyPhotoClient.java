package com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotomodified.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertyphotomodified.client.model.MALGetModifiedPropertyPhotoResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to MAL server end point /list_modified_property_photos.json
 */
@Component
public class MALGetModifiedPropertyPhotoClient extends MALSingleResponseClient<MALGetModifiedPropertyPhotoRequest, MALGetModifiedPropertyPhotoResponse> {

    private static final String URL_SEGMENT = "list_modified_property_photos.json";

    public MALGetModifiedPropertyPhotoClient() {
        super(MALGetModifiedPropertyPhotoResponse.class);
    }

    public RestResponse<MALGetModifiedPropertyPhotoResponse> download(final MALGetModifiedPropertyPhotoRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(URL_SEGMENT, null, HttpMethod.GET, params);
    }
}
