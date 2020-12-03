package com.brandmaker.mediapoolmalbridge.service.brandmaker.impl;

import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAsset;
import com.brandmaker.mediapoolmalbridge.model.brandmaker.asset.BMAssetResponse;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AssetRestService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/**
 * Asset Rest Service implementation.
 */
@Service
public class AssetRestServiceImpl implements AssetRestService {

    private final AuthorizationService authorizationService;

    private final RestTemplate restTemplate;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public AssetRestServiceImpl(AuthorizationService authorizationService, RestTemplate restTemplate) {
        this.authorizationService = authorizationService;
        this.restTemplate = restTemplate;
    }

    /**
     * {@inheritDoc}
     */
    public String getAssetsByThemeId(String themeId, String propertyId) {
        String filter = "[{\"type\":\"THEME_ID\",\"value\":[{\"search\":\"" + themeId + "\"},{\"search\":\"28\"}]}]";
        String url = "https://qamarriott.brandmakerinc.com/rest/mp/v1.0/search?filter={value}";
        HttpEntity<String> httpEntity = authorizationService.getHttpEntity();
        ResponseEntity<BMAssetResponse> responseEntity = getRestTemplate().exchange(url, HttpMethod.GET, httpEntity, BMAssetResponse.class, filter);
        if (responseEntity.getBody() != null) {
            List<BMAsset> bmAssets = responseEntity.getBody().getAssets();
            if (bmAssets != null && !bmAssets.isEmpty()) {
                for (BMAsset bmAsset : bmAssets) {
                    if (bmAsset.getAffiliateNumber() != null && bmAsset.getAffiliateNumber().equals(propertyId)) {
                        return bmAsset.getId();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method that return bean from RestTemplate class
     * @return RestTemplate object
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
