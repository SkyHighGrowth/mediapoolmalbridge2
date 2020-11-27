package com.brandmaker.mediapoolmalbridge.service.mal;

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
}
