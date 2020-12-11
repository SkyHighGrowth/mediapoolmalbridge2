package com.brandmaker.mediapoolmalbridge.security.service.iml;

import com.brandmaker.mediapoolmalbridge.security.JwtResponse;
import com.brandmaker.mediapoolmalbridge.security.service.AuthenticationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.AuthorizationService;
import com.brandmaker.mediapoolmalbridge.service.brandmaker.impl.AuthorizationServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private String authenticationServerUrl;

    private final AuthorizationService authorizationService;

    private static final String AUTHENTICATION_PATH = "/rest/sso/auth";

    public AuthenticationServiceImpl(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * This method is used for initialization
     */
    @PostConstruct
    public void init() {
        AuthorizationServiceImpl.Credentials credentials = authorizationService.getCredentials();
        String adminUsername = credentials.getUsername();
        String adminPassword = credentials.getPassword();
        new RestTemplateBuilder().basicAuthentication(adminUsername, adminPassword).build();
        authenticationServerUrl = credentials.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsernamePasswordAuthenticationToken authenticateUser(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("login", username);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<JwtResponse> responseEntity = null;
        try {
            responseEntity = new RestTemplate().postForEntity(String.format("%s%s", authenticationServerUrl, AUTHENTICATION_PATH), request, JwtResponse.class);
        } catch (RestClientException e) {
            logger.error("Authentication service is not available using username/password.", e);
        }
        if (responseEntity != null) {
            JwtResponse jwtResponse = responseEntity.getBody();
            if (jwtResponse == null || StringUtils.isEmpty(jwtResponse.getAccess_token())) {
                throw new BadCredentialsException("Bad credentials");
            }
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

}
