package com.theaudiochef.web.loginstub.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.domain.AppCredential;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;
import com.theaudiochef.web.loginstub.repository.AppCredentialRepository;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

@Service
public class AppCredentialServiceImpl implements AppCredentialService {
    
    @Value("${accessToken.expireMilliseconds}")
    private Integer expireMilliseconds;

    @Autowired
    AppAccountRepository appAccountRepository;
    
    @Autowired
    AmazonLoginService amazonLoginService;
    
    @Autowired
    AppCredentialRepository appCredentialRepository;
    
    @Autowired
    AmazonUserRepository amazonUserRepository;
    
    /**
     * will return the authorization code if successful
     * @param loginCredentials
     * @return
     */
    @Override
    public String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest) {
        AmazonUser amazonUser = amazonLoginService.processLogin(loginCredentials);
        
        if(amazonUser != null){
            AppAccount appAccount = appAccountRepository.findByClientId(authRequest.getClientId());
            AppCredential appCredential = new AppCredential(amazonUser, appAccount);
            appCredential.setExpireTime(LocalDateTime.now().plusNanos(expireMilliseconds * 1000000));
            appCredentialRepository.save(appCredential);
            return appCredential.getAuthorizationCode();
        }
        
        return null;
        
    }

    @Override
    public Profile retrieveProfile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValidAuthRequest(AuthRequest authRequest) {
        AppAccount appAccount = appAccountRepository.findByClientId(authRequest.getClientId());
        if(appAccount != null){
            return true;
        }else{
            return false;
        }
    }

    
}
