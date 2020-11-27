package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAssetsRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetAssetsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to list_asset.json end point to fetch assets modification, appearence
 */
@Component
public class MALGetAssetsClient extends MALSingleResponseClient<MALGetAssetsRequest, MALGetAssetsResponse> {

    private static final String URL_SEGMENT = "list_assets.json";

    public MALGetAssetsClient() {
        super(MALGetAssetsResponse.class);
    }

    public RestResponse<MALGetAssetsResponse> download(final MALGetAssetsRequest request) {
        final MultiValueMap<String, String> params = request.trnasformToGetParams();
        return exchange(URL_SEGMENT, null, HttpMethod.GET, params);
    }
}
