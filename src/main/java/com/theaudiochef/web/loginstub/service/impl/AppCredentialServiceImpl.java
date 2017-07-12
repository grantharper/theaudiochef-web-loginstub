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
import com.theaudiochef.web.loginstub.domain.ProfileError;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;
import com.theaudiochef.web.loginstub.repository.AppCredentialRepository;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

@Service
public class AppCredentialServiceImpl implements AppCredentialService {

    @Value("${accessToken.expireMilliseconds:300000}")
    private Integer accessTokenExpireMilliseconds;

    @Value("${authorizationCode.expireMilliseconds:300000}")
    private Integer authCodeExpireMilliseconds;

    private AppAccountRepository appAccountRepository;
    private AmazonLoginService amazonLoginService;
    private AppCredentialRepository appCredentialRepository;

    @Autowired
    public AppCredentialServiceImpl(AppAccountRepository appAccountRepository, AmazonLoginService amazonLoginService,
            AppCredentialRepository appCredentialRepository) {
        this.appAccountRepository = appAccountRepository;
        this.amazonLoginService = amazonLoginService;
        this.appCredentialRepository = appCredentialRepository;
    }

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

            // TODO handle situation where there is already an appCredential
            // created for the user?

            AppCredential appCredential = new AppCredential(amazonUser, appAccount, getAuthCodeExpireMilliseconds() * 1_000_000);
            appCredential.setAccessTokenExpireTime(LocalDateTime.now()
                                                                .plusNanos(
                                                                        getAccessTokenExpireMilliseconds() * 1_000_000));
            appCredentialRepository.save(appCredential);
            return appCredential.getAuthorizationCode();
        }

        return null;

    }

    @Override
    public ResponseEntity retrieveProfile(String accessToken) {
        AppCredential appCredential = appCredentialRepository.findByAccessToken(accessToken);
        if (appCredential != null) {
            return new ResponseEntity(new Profile(appCredential), HttpStatus.OK);
        }
        return new ResponseEntity(ProfileError.getProfileError(ProfileError.ERROR_TYPE.INVALID_ACCESS_TOKEN),
                HttpStatus.BAD_REQUEST);
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
    public AuthRequest generateAuthRequest(String clientId, String scope, String responseType, String redirectUri,
            String state) {

        AuthRequest authRequest = new AuthRequest(clientId, scope, responseType, redirectUri, state);

        if (isValidAuthRequest(authRequest)) {
            return authRequest;
        }
        return null;

    }

    @Override
    public ResponseEntity getAccessTokenFromAuthorizationCode(String authorizationCode, String clientId,
            String clientSecret) {

        AppCredential appCredential = null;
        // look up an app account by clientId and clientSecret
        AppAccount appAccount = appAccountRepository.findByClientIdAndClientSecret(clientId, clientSecret);

        // if not null then
        if (appAccount == null) {
            return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_CLIENT), HttpStatus.BAD_REQUEST);

        } else {
            // look up an app credential by authorization code
            appCredential = appCredentialRepository.findByAuthorizationCode(authorizationCode);

            if (appCredential == null) {
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            // verify the app credential looked up is for the supplied client id
            if (!appCredential.getAppAccount()
                              .getClientId()
                              .equals(appAccount.getClientId())) {
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_REQUEST),
                        HttpStatus.BAD_REQUEST);
            }

            if (appCredential.getAuthorizationCodeExpireTime()
                             .isBefore(LocalDateTime.now())) {
                return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_GRANT),
                        HttpStatus.BAD_REQUEST);
            }
        }

        AccessToken accessToken = new AccessToken(appCredential);

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAccessTokenFromRefreshToken(String refreshToken) {
        AppCredential appCredential = appCredentialRepository.findByRefreshToken(refreshToken);

        if (appCredential != null) {
            appCredential.resetAccessToken(LocalDateTime.now()
                                                        .plusNanos(accessTokenExpireMilliseconds * 1_000_000));

            appCredentialRepository.save(appCredential);

            AccessToken accessToken = new AccessToken(appCredential);

            return new ResponseEntity(accessToken, HttpStatus.OK);
        }

        return new ResponseEntity<>(AccessTokenError.getError(ERROR_TYPE.INVALID_REFRESH_TOKEN),
                HttpStatus.BAD_REQUEST);
    }

    public void setAccessTokenExpireMilliseconds(Integer expireMilliseconds) {
        this.accessTokenExpireMilliseconds = expireMilliseconds;
    }

    public Integer getAccessTokenExpireMilliseconds() {
        return accessTokenExpireMilliseconds;
    }

    public Integer getAuthCodeExpireMilliseconds() {
        return authCodeExpireMilliseconds;
    }

    public void setAuthCodeExpireMilliseconds(Integer authCodeExpireMilliseconds) {
        this.authCodeExpireMilliseconds = authCodeExpireMilliseconds;
    }
}
