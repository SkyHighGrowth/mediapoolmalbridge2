package com.brandmaker.mediapoolmalbridge.clients.mal.propertieswithrelatedassets.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.propertieswithrelatedassets.client.model.MALGetPropertiesWithRelatedAssetsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Client that wraps calls to MAL server /list_properties_with_related_assets.json
 */
@Component
public class MALGetPropertiesWithRelatedAssetsClient extends MALSingleResponseClient<Object, MALGetPropertiesWithRelatedAssetsResponse> {

    private static final String URL_SEGMENT = "list_properties_with_related_assets.json";

    public MALGetPropertiesWithRelatedAssetsClient() {
        super(MALGetPropertiesWithRelatedAssetsResponse.class);
    }

    public RestResponse<MALGetPropertiesWithRelatedAssetsResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}
