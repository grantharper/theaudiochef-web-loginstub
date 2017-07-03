package com.theaudiochef.web.loginstub.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppCredentialRepositoryIntegrationTest {

    @Autowired
    AppAccountRepository appAccountRepository;
    
    @Test
    public void testFindByClientId(){
        AppAccount appAccount = appAccountRepository.findByClientId("1");
        
        assertThat(appAccount.getClientId(), equalTo("1"));
    }
    
    
    
}
