package com.brandmaker.mediapoolmalbridge.clients.mal;

import com.brandmaker.mediapoolmalbridge.config.AppConfig;
import com.brandmaker.mediapoolmalbridge.config.AppConfigData;
import com.brandmaker.mediapoolmalbridge.persistence.repository.bridge.ReportsRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Class that holds common fields and mothods of MAL server clients
 */
public abstract class MALClient {

    protected static final Gson GSON = new Gson();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ReportsRepository reportsRepository;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    protected RestTemplate restTemplate;

    protected <REQUEST> String serializeRequestBody(final REQUEST request) {
        if (request == null) {
            return null;
        }
        return GSON.toJson(request);
    }

    protected URI createURL(final String urlSegment, final MultiValueMap<String, String> queryParameters) {
        AppConfigData appConfigData = appConfig.getAppConfigData();
        queryParameters.set("login", appConfigData.getMalLogin());
        queryParameters.set("api_key", appConfigData.getMalApiKey());
        return UriComponentsBuilder.fromHttpUrl(appConfigData.getMalHostname() + urlSegment)
                .queryParams(queryParameters)
                .build()
                .encode()
                .toUri();
    }
}
