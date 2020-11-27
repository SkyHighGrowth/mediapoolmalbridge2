package com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client.model.MALGetUnavailableAssetsRequest;
import com.brandmaker.mediapoolmalbridge.clients.mal.assetsunavailable.client.model.MALGetUnavailableAssetsResponse;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that call to MAL server end point list_unavailable_assets.json
 */
@Component
public class MALGetUnavailableAssetsClient extends MALSingleResponseClient<MALGetUnavailableAssetsRequest, MALGetUnavailableAssetsResponse> {

    private static final  String URL_SEGMENT = "list_unavailable_assets.json";

    public MALGetUnavailableAssetsClient() {
        super(MALGetUnavailableAssetsResponse.class);
    }

    public RestResponse<MALGetUnavailableAssetsResponse> download(final MALGetUnavailableAssetsRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(URL_SEGMENT, null, HttpMethod.GET, params);
    }
}
