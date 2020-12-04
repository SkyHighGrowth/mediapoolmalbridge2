package com.brandmaker.mediapoolmalbridge.service.brandmaker.impl;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.RestAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * This class is for server authorization with user/pass
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String AUTHENTICATION_URL = "/rest/sso/auth";

    private final AppConfig appConfig;

    private final RestTemplate restTemplate;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Environment environment;

    public static final String PRODUCTION = "production";

    public static final String DEV = "dev";

    public AuthorizationServiceImpl(AppConfig appConfigData, RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.appConfig = appConfigData;
        this.restTemplate = restTemplateBuilder.build();
        this.environment = environment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = setAuthorizationHeader();
        return new HttpEntity<>(headers);
    }

    /**
     * {@inheritDoc}
     */
    public String getDomainUrl() {
        final URL url;
        try {
            if (Arrays.asList(environment.getActiveProfiles()).contains(DEV) && !Arrays.asList(environment.getActiveProfiles()).contains(PRODUCTION)) {
                url = new URL(appConfig.getMediapoolUrlDev());
                return "https://" + url.getHost();
            } else if (Arrays.asList(environment.getActiveProfiles()).contains(PRODUCTION) && !Arrays.asList(environment.getActiveProfiles()).contains(DEV)) {
                url = new URL(appConfig.getMediapoolUrlProduction());
                return "https://" + url.getHost();
            }

        } catch (MalformedURLException e) {
            logger.error("URL is wrong in the application.properties file for MediaPoolUrl property");
        }
        return null;
    }

    private RestAccessToken authenticateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Credentials credentials = getCredentials();
        params.add("login", credentials.getUsername());
        params.add("password", credentials.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            return getRestTemplate().postForObject(getAuthenticationURL(), request, RestAccessToken.class);
        } catch (RestClientException e) {
            logger.error("Authentication service is not available.", e);
        }

        return null;
    }

    private HttpHeaders setAuthorizationHeader() {
        RestAccessToken authenticationToken = authenticateUser();

        HttpHeaders headers = new HttpHeaders();
        if (authenticationToken != null) {
            String bearerToken = String.format("Bearer %s", authenticationToken.getAccessToken());
            headers.set("Authorization", bearerToken);
        }

        return headers;
    }

    private String getAuthenticationURL() {
        return String.format("%s%s", getCredentials().getUrl(), AuthorizationServiceImpl.AUTHENTICATION_URL);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private Credentials getCredentials() {
        Credentials credentials = new Credentials();
        if (Arrays.asList(environment.getActiveProfiles()).contains(DEV) && !Arrays.asList(environment.getActiveProfiles()).contains(PRODUCTION)) {
            credentials.setUsername(appConfig.getMediapoolUsernameDev());
            credentials.setPassword(appConfig.getMediapoolPasswordDev());
            credentials.setUrl(getDomainUrl());
        }
        if (Arrays.asList(environment.getActiveProfiles()).contains(PRODUCTION) && !Arrays.asList(environment.getActiveProfiles()).contains(DEV)) {
            credentials.setUsername(appConfig.getMediapoolUsernameProduction());
            credentials.setPassword(appConfig.getMediapoolPasswordProduction());
            credentials.setUrl(getDomainUrl());
        }

        return credentials;
    }

    static class Credentials {
        private String username;
        private String password;
        private String url;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getUrl() {
            return url;
        }
    }
}