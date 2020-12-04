package com.brandmaker.mediapoolmalbridge.service.brandmaker;

import org.springframework.http.HttpEntity;

/**
 * This class is for server authorization with user/pass
 */
public interface AuthorizationService {

    /**
     * Get {@link HttpEntity}
     *
     * @return {@link String}
     */
    HttpEntity<String> getHttpEntity();

    /**
     * Get domain URL
     *
     * @return the domain URL
     */
    String getDomainUrl();
}
