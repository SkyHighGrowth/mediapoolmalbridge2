package com.brandmaker.mediapoolmalbridge.service.brandmaker.impl;

import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAsset;
import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAssetResponse;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AssetRestService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Asset Rest Service implementation
 */
@Service
public class AssetRestServiceImpl implements AssetRestService {

    public static final String SEARCH_URL = "/rest/mp/v1.0/search?filter={filter}&limit={limit}";
    public static final int ASSET_TYPE_LOGO = 28;

    private final AuthorizationService authorizationService;

    private final RestTemplate restTemplate;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * AssetRestServiceImpl constructor
     *
     * @param authorizationService {@link AuthorizationService}
     * @param restTemplate         {@link RestTemplate}
     */
    public AssetRestServiceImpl(AuthorizationService authorizationService, RestTemplate restTemplate) {
        this.authorizationService = authorizationService;
        this.restTemplate = restTemplate;
    }

    /**
     * {@inheritDoc}
     */
    public List<BMAsset> getAssetIdsByThemeIdAndPropertyId(String themeId) {

        String filter = getFilterByThemeId(themeId);
        String url = authorizationService.getDomainUrl() + SEARCH_URL;
        HttpEntity<String> httpEntity = authorizationService.getHttpEntity();
        ResponseEntity<BMAssetResponse> responseEntity = getRestTemplate()
                .exchange(url, HttpMethod.GET, httpEntity, BMAssetResponse.class, filter, "3000");
        BMAssetResponse responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null && responseEntityBody.getAssets() != null && !responseEntityBody.getAssets().isEmpty()) {
            return responseEntityBody.getAssets();
        }
        return new ArrayList<>();
    }

    private String getFilterByThemeId(String themeId) {
        JsonArray valueObject = new JsonArray();

        JsonObject themeIdObject = new JsonObject();
        themeIdObject.addProperty("search", themeId);

        JsonObject assetTypeIdObject = new JsonObject();
        assetTypeIdObject.addProperty("search", ASSET_TYPE_LOGO);

        valueObject.add(themeIdObject);
        valueObject.add(assetTypeIdObject);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "THEME_ID");
        jsonObject.add("value", valueObject);

        JsonArray result = new JsonArray();
        result.add(jsonObject);

        return result.toString();
    }


    /**
     * Method that return bean from RestTemplate class
     *
     * @return RestTemplate object
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
