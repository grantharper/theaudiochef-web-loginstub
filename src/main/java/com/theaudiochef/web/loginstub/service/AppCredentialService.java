package com.theaudiochef.web.loginstub.service;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;

public interface AppCredentialService {
    
    public boolean isValidAuthRequest(AuthRequest authRequest);

    public String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest);
    
    public Profile retrieveProfile();
}
