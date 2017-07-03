package com.theaudiochef.web.loginstub.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AppCredential extends AbstractDomainClass {

    @ManyToOne
    private AmazonUser user;

    @ManyToOne
    private AppAccount app;

    private String accessToken;

    private String authorizationCode;

    private String appUserId;
    private String refreshToken;
    private LocalDateTime expireTime;
    private String amazonTokenType;

    public AppCredential() {
    }

    /**
     * this constructor will create a user record for a new app that they would
     * like to access
     * 
     * @param user
     * @param appId
     */
    public AppCredential(AmazonUser user, AppAccount appAccount) {
        this.user = user;
        this.app = appAccount;
        this.appUserId = generateAppUserId();
        this.amazonTokenType = "code";
        this.authorizationCode = generateAuthorizationCode();
        this.accessToken = generateAccessToken();
        this.refreshToken = generateRefreshToken();

    }

    private String generateAppUserId() {
        return UUID.randomUUID()
                   .toString();
    }
    
    private String generateAuthorizationCode(){
        return UUID.randomUUID().toString();
    }
    
    private String generateAccessToken(){
        return UUID.randomUUID().toString();
    }
    
    private String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    public AmazonUser getUser() {
        return user;
    }

    public void setUser(AmazonUser user) {
        this.user = user;
    }

    public AppAccount getApp() {
        return app;
    }

    public void setApp(AppAccount app) {
        this.app = app;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getAmazonTokenType() {
        return amazonTokenType;
    }

    public void setAmazonTokenType(String amazonTokenType) {
        this.amazonTokenType = amazonTokenType;
    }

}
