package com.theaudiochef.web.loginstub.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = Logger.getLogger(SpringJpaBootstrap.class);

    @Autowired
    Environment env;

    @Autowired
    AmazonUserRepository userRepository;
    
    @Autowired
    AppAccountRepository appAccountRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadApps();
    }

    private void loadUsers() {
        log.info("loading dummy user data to database");

        AmazonUser user1 = new AmazonUser();
        user1.setName("Grant");
        user1.setEmail("mail@theaudiochef.com");
        user1.setUsername("gharper");
        user1.setPassword("password");
        userRepository.save(user1);
    }

    private void loadApps() {
        log.info("loading dummy app data to the database");

        AppAccount app1 = new AppAccount();
        app1.setClientId("1");
        app1.setClientName("theaudiochef");
        app1.setClientSecret("super-secret");
        appAccountRepository.save(app1);

    }

}
