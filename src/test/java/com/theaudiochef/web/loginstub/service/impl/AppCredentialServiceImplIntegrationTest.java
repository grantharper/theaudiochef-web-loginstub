package com.theaudiochef.web.loginstub.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.domain.AppCredential;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;
import com.theaudiochef.web.loginstub.repository.AppCredentialRepository;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AppCredentialServiceImplIntegrationTest {
    
    private static final String TEST_CLIENT_ID = "TEST";
    private static final String TEST_NONEXISTING_CLIENT_ID = "DNE";
    private static final String TEST_USERNAME = "user1";
    private static final String TEST_PASSWORD = "pw";
    private static final String AUTH_CODE = "blahblahblah";

    @InjectMocks
    AppCredentialServiceImpl classUnderTest;
    
    @Mock
    AppAccountRepository appAccountRepository;
    
    @Mock
    AppCredentialRepository appCredentialRepository;
    
    @Mock
    AmazonLoginService amazonLoginService;
    
    @Mock
    AppAccount appAccount;
    
    @Mock
    AmazonUser amazonUser;
    
    @Spy
    AppCredential appCredential;
    
    @Mock
    private AuthRequest authRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        classUnderTest.setExpireMilliseconds(300);
        when(amazonLoginService.processLogin(amazonUser)).thenReturn(amazonUser);
        when(authRequest.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(appAccountRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(appAccount);
        when(appAccountRepository.findByClientId(TEST_NONEXISTING_CLIENT_ID)).thenReturn(null);
        
    }

    @Test
    public void testValidAuthRequest() {

        assertTrue(classUnderTest.isValidAuthRequest(authRequest));

    }

    @Test
    public void testCreateAppCredential() {
        when(amazonUser.getUsername()).thenReturn(TEST_USERNAME);
        when(amazonUser.getPassword()).thenReturn(TEST_PASSWORD);
        //when(appCredential.getAuthorizationCode()).thenReturn(AUTH_CODE);
        String authCode = classUnderTest.createAppCredential(amazonUser, authRequest);
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
