package com.brandmaker.mediapoolmalbridge.service.brandmaker;

import com.brandmaker.mediapoolmalbridge.service.brandmaker.impl.AuthorizationServiceImpl;
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

    /**
     * Get Credentials
     *
     * @return Credentials {@link AuthorizationServiceImpl.Credentials}
     */
    AuthorizationServiceImpl.Credentials getCredentials();
}
