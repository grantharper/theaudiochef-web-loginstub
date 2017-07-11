package com.theaudiochef.web.loginstub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AppAccount extends AbstractDomainClass{

    @Column(name="client_id")
    private String clientId;
    
    @Column(name="client_name")
    private String clientName;
    
    @Column(name="client_secret")
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
