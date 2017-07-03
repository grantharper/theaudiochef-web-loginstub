package com.theaudiochef.web.loginstub.domain;

import javax.persistence.Entity;

@Entity
public class AppAccount extends AbstractDomainClass{

    private String clientId;
    
    private String clientName;
    
    private String clientSecret;
    
    public AppAccount(){}

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    };
    
    
    
}
