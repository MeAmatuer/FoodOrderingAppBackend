/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: BearerAuthDecoder.java
 * Date: Nov 10, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package com.upgrad.FoodOrderingApp.api.provider;

import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;

/**
 * Provider to decode bearer token.
 */
public class BearerAuthDecoder {

    private final String accessToken;
    private final String BEARER_AUTH_PREFIX = "Bearer";

    public BearerAuthDecoder(final String bearerToken) throws AuthorizationFailedException{
        if(!bearerToken.startsWith(BEARER_AUTH_PREFIX)) {
            throw new AuthorizationFailedException("ATHR-004", "Invalid Authorization header format");
        }

        final String[] bearerTokens = bearerToken.split(BEARER_AUTH_PREFIX);
        if(bearerTokens.length != 2) {
            throw new AuthorizationFailedException("ATHR-004", "Invalid Authorization header format");
        }
        this.accessToken = bearerTokens[1];
    }

    public String getAccessToken() {
        return accessToken;
    }

}