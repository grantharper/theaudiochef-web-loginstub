package com.theaudiochef.web.loginstub.domain;

public class AuthRequest {

    private String clientId;
    private String scope;
    private String responseType;
    private String redirectUri;
    private String state;
    
    public AuthRequest(String clientId, String scope, String responseType, String redirectUri, String state){
        
        this.clientId = clientId;
        this.scope =  scope;
        this.responseType = responseType;
        this.redirectUri = redirectUri;
        this.state = state;
        
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
    
}
