package com.theaudiochef.web.loginstub.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppCredentialServiceImplIntegrationTest {

    @Autowired
    AppCredentialService appCredentialService;
    
    private AuthRequest authRequest;
    
    @Before
    public void setup(){
        authRequest = new AuthRequest("1", "profile", "code", "fake", "state1");
    }
    
    @Test
    public void testValidAuthRequest() {
        
        assertTrue(appCredentialService.isValidAuthRequest(authRequest));
        
    }

}
