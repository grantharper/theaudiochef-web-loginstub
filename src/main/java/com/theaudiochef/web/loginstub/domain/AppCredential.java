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
    private LocalDateTime authorizationCodeExpireTime;

    private String appUserId;
    private String refreshToken;
    private LocalDateTime accessTokenExpireTime;
    private String amazonTokenType;

    public AppCredential() {}

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
        this.authorizationCodeExpireTime = LocalDateTime.now().plusMinutes(5);
        this.accessToken = generateAccessToken();
        this.refreshToken = generateRefreshToken();

    }

    protected String generateAppUserId() {
        return generateRandomUUID();
    }
    
    protected String generateAuthorizationCode(){
        return generateRandomUUID();
    }
    
    protected String generateAccessToken(){
        return generateRandomUUID();
    }
    
    protected String generateRefreshToken(){
        return generateRandomUUID();
    }
    
    protected String generateRandomUUID(){
        return UUID.randomUUID().toString();
    }
    
    

    public LocalDateTime getAuthorizationCodeExpireTime() {
        return authorizationCodeExpireTime;
    }

    public void setAuthorizationCodeExpireTime(LocalDateTime authorizationCodeExpireTime) {
        this.authorizationCodeExpireTime = authorizationCodeExpireTime;
    }

    public LocalDateTime getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(LocalDateTime accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
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

    public String getAmazonTokenType() {
        return amazonTokenType;
    }

    public void setAmazonTokenType(String amazonTokenType) {
        this.amazonTokenType = amazonTokenType;
    }

}
