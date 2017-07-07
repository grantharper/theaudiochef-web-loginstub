package com.theaudiochef.web.loginstub.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppCredentialServiceImplIntegrationTest {

    @Autowired
    AppCredentialService appCredentialService;

    private AuthRequest authRequest;

    @Before
    public void setup() {
        authRequest = new AuthRequest("1", "profile", "code", "fake", "state1");
    }

    @Test
    public void testValidAuthRequest() {

        assertTrue(appCredentialService.isValidAuthRequest(authRequest));

    }

    @Test
    public void testCreateAppCredential() {
        //AmazonLoginService loginService = mock(AmazonLoginService.class);
        AmazonUser user = mock(AmazonUser.class);
        when(user.getUsername()).thenReturn("gharper");
        when(user.getPassword()).thenReturn("password");
        //when(loginService.processLogin(user)).thenReturn(user);
        String authCode = appCredentialService.createAppCredential(user, authRequest);
        assertThat(authCode.length(), equalTo(36));
    }

    @Ignore
    @Test
    public void testGetAccessTokenFromAuthorizationCode() {

    }

    @Ignore
    @Test
    public void testRetrieveProfile() {

    }

}
