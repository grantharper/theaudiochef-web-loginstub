package com.theaudiochef.web.loginstub.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AppCredential;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppCredentialRepositoryIntegrationTest {

    @Autowired
    AppCredentialRepository appCredentialRepository;
    
    //NOTE: These tests are dependent on the JpaBootstrapping class to load the data

    @Test
    public void testFindByAuthorizationCode(){
        AppCredential appCredential = appCredentialRepository.findByAuthorizationCode("AC");
        assertThat(appCredential.getAuthorizationCode(), equalTo("AC"));
        
    }
    
    @Test
    public void testFindByRefreshToken(){
        AppCredential appCredential = appCredentialRepository.findByRefreshToken("RT");
        assertThat(appCredential.getRefreshToken(), equalTo("RT"));
    }
    
    @Test
    public void testFindByAccessToken() {
        AppCredential appCredential = appCredentialRepository.findByAccessToken("AT");
        assertThat(appCredential.getAccessToken(), equalTo("AT"));
        
    }
    
}
