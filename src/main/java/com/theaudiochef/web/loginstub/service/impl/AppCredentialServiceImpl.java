package com.theaudiochef.web.loginstub.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.theaudiochef.web.loginstub.domain.AccessToken;
import com.theaudiochef.web.loginstub.domain.AccessTokenError;
import com.theaudiochef.web.loginstub.domain.AccessTokenError.ERROR_TYPE;
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
     * 
     * @param loginCredentials
     * @return
     */
    @Override
    public String createAppCredential(AmazonUser loginCredentials, AuthRequest authRequest) {
        AmazonUser amazonUser = amazonLoginService.processLogin(loginCredentials);

        if (amazonUser != null) {
            AppAccount appAccount = appAccountRepository.findByClientId(authRequest.getClientId());
            
            //TODO handle situation where there is already an appCredential created for the user?
            
            AppCredential appCredential = new AppCredential(amazonUser, appAccount);
            appCredential.setAccessTokenExpireTime(LocalDateTime.now()
                                                     .plusNanos(expireMilliseconds * 1000000));
            appCredentialRepository.save(appCredential);
            return appCredential.getAuthorizationCode();
        }

        return null;

    }

    @Override
    public Profile retrieveProfile(String accessToken) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValidAuthRequest(AuthRequest authRequest) {
        AppAccount appAccount = appAccountRepository.findByClientId(authRequest.getClientId());
        if (appAccount != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getAccessTokenFromAuthorizationCode(String authorizationCode, String clientId, String clientSecret) {

        AppCredential appCredential = null;
        // look up an app account by clientId and clientSecret
        AppAccount appAccount = appAccountRepository.findByClientIdAndClientSecret(clientId, clientSecret);

        // if not null then
        if (appAccount == null) {
            return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_CLIENT), HttpStatus.BAD_REQUEST);

        } else {
            // look up an app credential by authorization code and verify it is
            // for the clientId supplied
            appCredential = appCredentialRepository.findByAuthorizationCode(authorizationCode);

            if (appCredential == null) {
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            if (!appCredential.getApp()
                              .getClientId()
                              .equals(appAccount.getClientId())) {
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }
            
            if(appCredential.getAuthorizationCodeExpireTime().isBefore(LocalDateTime.now())){
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_GRANT),
                        HttpStatus.BAD_REQUEST);
            }
        }

        AccessToken accessToken = new AccessToken(appCredential);

        return accessToken;
    }

}
