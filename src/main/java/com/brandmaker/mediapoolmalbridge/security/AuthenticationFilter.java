package com.brandmaker.mediapoolmalbridge.security;


import com.brandmaker.mediapoolmalbridge.security.service.AuthenticationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    private final AuthorizationService authorizationService;

    public AuthenticationFilter(AuthenticationService authenticationService, AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            UsernamePasswordAuthenticationToken user = getUserThroughAuthorizationHeader(authorizationHeader);
            if (user == null) {
                throw new AccessDeniedException("You are not authorized!");
            }

            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader("redirectUrl", authorizationService.getDomainUrl());
            filterChain.doFilter(request, response);
        }
    }


    private UsernamePasswordAuthenticationToken getUserThroughAuthorizationHeader(String authorizationHeader) {
        if (StringUtils.isBlank(authorizationHeader)) {
            return null;
        }

        String[] basicAuthorizationHeader = authorizationHeader.split("Basic");
        String base64UsernameAndPassword = basicAuthorizationHeader[1].trim();
        String usernameAndPassword = new String(Base64.getDecoder().decode(base64UsernameAndPassword));
        String[] usernameAndPasswordSplit = usernameAndPassword.split(":");
        String username = usernameAndPasswordSplit[0];
        String password = usernameAndPasswordSplit[1];

        return authenticationService.authenticateUser(username, password);
    }
}