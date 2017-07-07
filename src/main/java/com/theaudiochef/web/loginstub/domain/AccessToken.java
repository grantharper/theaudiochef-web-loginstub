package com.theaudiochef.web.loginstub.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class AccessToken {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    
    public AccessToken(){}
    
    public AccessToken(AppCredential appCredential){
        this.access_token = appCredential.getAccessToken();
        this.token_type = "bearer";
        this.expires_in = Duration.between(LocalDateTime.now() , appCredential.getAccessTokenExpireTime()).getSeconds();
        this.refresh_token = appCredential.getRefreshToken();
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    
    
}
