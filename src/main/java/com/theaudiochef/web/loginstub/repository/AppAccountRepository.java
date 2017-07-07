package com.theaudiochef.web.loginstub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theaudiochef.web.loginstub.domain.AppAccount;

@Repository
public interface AppAccountRepository extends JpaRepository<AppAccount, Long> {

    public AppAccount findByClientId(String clientId);
    
    public AppAccount findByClientIdAndClientSecret(String clientId, String clientSecret);
    
}
