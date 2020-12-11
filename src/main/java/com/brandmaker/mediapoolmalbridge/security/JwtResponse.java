package com.brandmaker.mediapoolmalbridge.security;


/**
 * This class represent response from authentication service.
 * From field <codse>access_token</codse> user name is extracted.
 */
public class JwtResponse {

    private String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
