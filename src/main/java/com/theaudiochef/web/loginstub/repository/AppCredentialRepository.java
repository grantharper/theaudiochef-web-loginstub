package com.theaudiochef.web.loginstub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theaudiochef.web.loginstub.domain.AppCredential;

@Repository
public interface AppCredentialRepository extends JpaRepository<AppCredential, Long>{

    public AppCredential findByAuthorizationCode(String authorizationCode);
}
