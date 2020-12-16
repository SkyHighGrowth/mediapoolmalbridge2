package com.brandmaker.mediapoolmalbridge.security.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * This is interface that handles authentication
 */
public interface AuthenticationService {

    /**
     * This method is used for authenticating users from username and password.
     *
     * @param username {@link String}
     * @param password {@link String}
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    UsernamePasswordAuthenticationToken authenticateUser(String username, String password);

    /**
     * This method is used for authenticating users from cookie.
     *
     * @param jSessionIdSso {@link String}
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    UsernamePasswordAuthenticationToken authenticateUser(String jSessionIdSso);

}
