package com.brandmaker.mediapoolmalbridge.service.brandmaker.impl;

import com.brandmaker.mediapoolmalbridge.model.brandmaker.theme.BMTheme;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.ThemeRestService;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ThemeRestServiceImpl implements ThemeRestService {

    private final AuthorizationService authorizationService;

    private final RestTemplate restTemplate;

    public ThemeRestServiceImpl(AuthorizationService authorizationService, RestTemplate restTemplate) {
        this.authorizationService = authorizationService;
        this.restTemplate = restTemplate;
    }


    public String getColorIdByName(String name) {
        HttpEntity<String> httpEntity = authorizationService.getHttpEntity();

        List<BMTheme> forEntity = getRestTemplate().getForObject("https://marriott.brandmakerinc.com/rest/mp/v1.1/themes", List.class, httpEntity);

        List<BMTheme> body = forEntity;
        if (body != null) {
            for (BMTheme bmTheme : body) {
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


    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
