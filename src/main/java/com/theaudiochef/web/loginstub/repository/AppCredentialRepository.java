package com.theaudiochef.web.loginstub.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theaudiochef.web.loginstub.domain.AppCredential;

@Repository
@Transactional
public interface AppCredentialRepository extends JpaRepository<AppCredential, Long>{

    AppCredential findByAuthorizationCode(String authorizationCode);
    
    AppCredential findByRefreshToken(String refreshToken);
    
    AppCredential findByAccessToken(String accessToken);
}
