package com.theaudiochef.web.loginstub.service;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;

public interface AppCredentialService {
    
    boolean isValidAuthRequest(AuthRequest authRequest);

    String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest);
    
    Object getAccessTokenFromAuthorizationCode(String authorizationCode, String clientId, String clientSecret);
    
    Profile retrieveProfile(String accessToken);
}
