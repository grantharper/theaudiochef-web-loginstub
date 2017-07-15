package com.theaudiochef.web.loginstub.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.ui.Model;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

public class MainControllerTest {

    private static final String TEST_CLIENT_ID = "TEST";
    private static final String TEST_SCOPE = "profile";
    private static final String TEST_RESPONSE_TYPE = "ac";
    private static final String REDIRECT_URI = "/redirect";
    private static final String STATE = "1234-state";
    private static final String AUTH_CODE = "blahblahblah";

    @InjectMocks
    MainController classUnderTest;

    @Mock
    AppCredentialService appCredentialService;

    @Mock
    AuthRequest authRequest;

    @Mock
    Model model;

    @Mock
    Session session;
    
    @Mock
    AmazonUser amazonUser;
    
    @Before
    public void setup() {
        classUnderTest = new MainController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessAuthRequestSuccess() {
        when(appCredentialService.generateAuthRequest(TEST_CLIENT_ID, TEST_SCOPE, TEST_RESPONSE_TYPE, REDIRECT_URI,
                STATE)).thenReturn(authRequest);

        String result = classUnderTest.processAuthRequest(model, session, TEST_CLIENT_ID, TEST_SCOPE,
                TEST_RESPONSE_TYPE, REDIRECT_URI, STATE);
        
        assertThat(result, equalTo("redirect:/login"));

    }
    
    @Test
    public void testProcessAuthRequestFailure() {
        when(appCredentialService.generateAuthRequest(TEST_CLIENT_ID, TEST_SCOPE, TEST_RESPONSE_TYPE, REDIRECT_URI,
                STATE)).thenReturn(null);
        
        String result = classUnderTest.processAuthRequest(model, session, TEST_CLIENT_ID, TEST_SCOPE,
                TEST_RESPONSE_TYPE, REDIRECT_URI, STATE);
        
        assertThat(result, equalTo("redirect:" + REDIRECT_URI + "?error=invalid_parameters"));
    }
    
    @Test
    public void testSubmitLoginSuccess() {
        when(appCredentialService.createAppCredential(amazonUser, authRequest)).thenReturn(AUTH_CODE);
        when(authRequest.getRedirectUri()).thenReturn(REDIRECT_URI);
        when(authRequest.getState()).thenReturn(STATE);
        
        String result = classUnderTest.submitLogin(model, amazonUser, authRequest);
        assertThat(result, equalTo("redirect:" + REDIRECT_URI + "?code=" + AUTH_CODE + "&state=" + STATE));
        
    }
    
    @Test
    public void testSubmitLoginFailure() {
        when(appCredentialService.createAppCredential(amazonUser, authRequest)).thenReturn(null);
        when(authRequest.getRedirectUri()).thenReturn(REDIRECT_URI);
        when(authRequest.getState()).thenReturn(STATE);
        
        String result = classUnderTest.submitLogin(model, amazonUser, authRequest);
        assertThat(result, equalTo("redirect:" + REDIRECT_URI + "?error=access_denied&state=" + STATE));
    }

    
    
}
