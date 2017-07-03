package com.theaudiochef.web.loginstub.service;

import com.theaudiochef.web.loginstub.domain.AmazonUser;

public interface AmazonLoginService {

    AmazonUser processLogin(AmazonUser loginCredentials);
    
}
