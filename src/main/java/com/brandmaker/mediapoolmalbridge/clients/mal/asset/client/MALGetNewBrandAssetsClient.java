package com.brandmaker.mediapoolmalbridge.clients.mal.asset.client;

import com.brandmaker.mediapoolmalbridge.clients.mal.MALSingleResponseClient;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetNewAssetsResponse;
import com.brandmaker.mediapoolmalbridge.clients.mal.asset.client.model.MALGetNewBrandAssetsRequest;
import com.brandmaker.mediapoolmalbridge.clients.rest.RestResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * Client that wraps calls to api/syncs end point to fetch new brand assets
 */
@Component
public class MALGetNewBrandAssetsClient extends MALSingleResponseClient<MALGetNewBrandAssetsRequest, MALGetNewAssetsResponse> {

    private static final String URL_SEGMENT = "api/syncs";

    public MALGetNewBrandAssetsClient() {
        super(MALGetNewAssetsResponse.class);
    }

    /**
     * Download assets wirth requested parameters
     *
     * @param request {@link MALGetNewBrandAssetsRequest}
     * @return response {@link MALGetNewAssetsResponse}
     */
    public RestResponse<MALGetNewAssetsResponse> download(final MALGetNewBrandAssetsRequest request) {
        final MultiValueMap<String, String> params = request.transformToGetParams();
        return exchange(URL_SEGMENT, null, HttpMethod.GET, params);
    }
}