package com.theaudiochef.web.loginstub.bootstrap;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.domain.AppCredential;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;
import com.theaudiochef.web.loginstub.repository.AppCredentialRepository;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = Logger.getLogger(SpringJpaBootstrap.class);

    @Autowired
    Environment env;

    @Autowired
    AmazonUserRepository userRepository;
    
    @Autowired
    AppAccountRepository appAccountRepository;
    
    @Autowired
    AppCredentialRepository appCredentialRepository;
    
    private AmazonUser user1;
    private AppAccount app1;
    private AppCredential appCred1;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadApps();
        loadAppCredentials();
    }

    private void loadUsers() {
        log.info("loading dummy user data to database");

        user1 = new AmazonUser();
        user1.setName("Grant");
        user1.setEmail("mail@theaudiochef.com");
        user1.setUsername("gharper");
        user1.setPassword("password");
        userRepository.save(user1);
    }

    private void loadApps() {
        log.info("loading dummy app data to the database");

        app1 = new AppAccount();
        app1.setClientId("1");
        app1.setClientName("theaudiochef");
        app1.setClientSecret("secret");
        appAccountRepository.save(app1);

    }
    
    private void loadAppCredentials() {
        log.info("loading app credential data to the database");
        
        appCred1 = new AppCredential(user1, app1, 300000);
        appCred1.setAuthorizationCode("AC");
        appCred1.setAccessToken("AT");
        appCred1.setAppUserId("AUID");
        appCred1.setRefreshToken("RT");
        appCred1.setAccessTokenExpireTime(LocalDateTime.now().plusMinutes(5));
        appCred1.setAuthorizationCodeExpireTime(LocalDateTime.now().plusMinutes(1));
        
        appCredentialRepository.save(appCred1);
        
    }

}
