package com.theaudiochef.web.loginstub.service;

import org.springframework.http.ResponseEntity;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;

public interface AppCredentialService {
    
    boolean isValidAuthRequest(AuthRequest authRequest);

    String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest);
    
    ResponseEntity getAccessTokenFromAuthorizationCode(String authorizationCode, String clientId, String clientSecret);
    
    Profile retrieveProfile(String accessToken);
}
