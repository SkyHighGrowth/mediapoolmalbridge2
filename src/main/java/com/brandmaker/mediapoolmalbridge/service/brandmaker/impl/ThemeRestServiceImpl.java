package com.brandmaker.mediapoolmalbridge.service.brandmaker.impl;

import com.brandmaker.mediapoolmalbridge.model.brandmaker.theme.BMTheme;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.ThemeRestService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * BM Theme REST service for fetching color ID by color name
 */
@Service
public class ThemeRestServiceImpl implements ThemeRestService {

    public static final String THEME_URL = "/rest/mp/v1.1/themes";
    private final AuthorizationService authorizationService;

    private final RestTemplate restTemplate;

    /**
     * ThemeRestServiceImpl constructor
     *
     * @param authorizationService {@link AuthorizationService}
     * @param restTemplate         {@link RestTemplate}
     */
    public ThemeRestServiceImpl(AuthorizationService authorizationService, RestTemplate restTemplate) {
        this.authorizationService = authorizationService;
        this.restTemplate = restTemplate;
    }


    /**
     * {@inheritDoc}
     */
    public String getColorIdByName(String name) {
        HttpEntity<String> httpEntity = authorizationService.getHttpEntity();

        ResponseEntity<BMTheme[]> forEntity = new RestTemplate()
                .exchange(authorizationService.getDomainUrl() + THEME_URL, HttpMethod.GET, httpEntity, BMTheme[].class);

        if (forEntity.getBody() != null) {
            for (BMTheme bmTheme : forEntity.getBody()) {
                if (bmTheme.getName().equals("Colors")) {
                    List<BMTheme> children = bmTheme.getChildren();
                    for (BMTheme child : children) {
                        if (child.getName().equals(name)) {
                            return child.getId();
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Rest template
     *
     * @return {@link RestTemplate}
     */
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
