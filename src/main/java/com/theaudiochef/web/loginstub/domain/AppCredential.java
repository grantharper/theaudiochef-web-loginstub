package com.theaudiochef.web.loginstub.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AppCredential extends AbstractDomainClass {

    @ManyToOne
    private AmazonUser user;

    @ManyToOne
    @JoinColumn(name="app_account")
    private AppAccount appAccount;
    
    @Column(name="app_user_id")
    private String appUserId;

    @Column(name="access_token")
    private String accessToken;
    
    @Column(name="access_token_expire_time")
    private LocalDateTime accessTokenExpireTime;
    
    @Column(name="amazon_token_type")
    private String amazonTokenType;
    
    @Column(name="refresh_token")
    private String refreshToken;
    
    @Column(name="authorization_code")
    private String authorizationCode;
    
    @Column(name="authorization_code_expire_time")
    private LocalDateTime authorizationCodeExpireTime;

    public AppCredential() {}

    /**
     * this constructor will create a user record for a new app that they would
     * like to access
     * 
     * @param user
     * @param appId
     */
    public AppCredential(AmazonUser user, AppAccount appAccount, Integer nanoSecondsToAuthCodeExpiration) {
        this.user = user;
        this.appAccount = appAccount;
        this.appUserId = generateAppUserId();
        this.amazonTokenType = "code";
        this.authorizationCode = generateAuthorizationCode();
        this.authorizationCodeExpireTime = LocalDateTime.now().plusNanos(nanoSecondsToAuthCodeExpiration);
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
    
    public void resetAccessToken(LocalDateTime updatedAccessTokenExpireTime) {
        setAccessToken(generateAccessToken());
        setAccessTokenExpireTime(updatedAccessTokenExpireTime);
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

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount app) {
        this.appAccount = app;
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
