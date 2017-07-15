package com.theaudiochef.web.loginstub.service;

import org.springframework.http.ResponseEntity;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;

public interface AppCredentialService {
    
    boolean isValidAuthRequest(AuthRequest authRequest);

    String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest);
    
    ResponseEntity<Object> getAccessTokenFromAuthorizationCode(String authorizationCode, String clientId, String clientSecret);
    
    ResponseEntity<Object> getAccessTokenFromRefreshToken(String refreshToken);
    
    ResponseEntity<Object> retrieveProfile(String accessToken);

    AuthRequest generateAuthRequest(String clientId, String scope, String responseType, String redirectUri,
            String state);
}
