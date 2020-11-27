package com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.assettypes.client.model.MALGetAssetTypesResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MALGetAssetTypesClient extends MALSingleResponseClient<Object, MALGetAssetTypesResponse> {

    private static final String URL_SEGMENT = "list_asset_types.json";

    public MALGetAssetTypesClient() {
        super(MALGetAssetTypesResponse.class);
    }

    public RestResponse<MALGetAssetTypesResponse> download() {
        return exchange(URL_SEGMENT, null, HttpMethod.GET);
    }
}