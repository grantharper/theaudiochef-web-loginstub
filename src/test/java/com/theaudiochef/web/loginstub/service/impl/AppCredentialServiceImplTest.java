package com.theaudiochef.web.loginstub.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.theaudiochef.web.loginstub.domain.AccessToken;
import com.theaudiochef.web.loginstub.domain.AccessTokenError;
import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AppAccount;
import com.theaudiochef.web.loginstub.domain.AppCredential;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;
import com.theaudiochef.web.loginstub.domain.ProfileError;
import com.theaudiochef.web.loginstub.repository.AppAccountRepository;
import com.theaudiochef.web.loginstub.repository.AppCredentialRepository;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;

public class AppCredentialServiceImplTest {

    private static final String TEST_CLIENT_ID = "TEST";
    private static final String TEST_CLIENT_SECRET = "TEST_SECRET";
    private static final String TEST_ACCESS_TOKEN = "TEST_TOKEN";
    private static final String TEST_REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String TEST_APP_USER_ID = "APP_USER_ID";
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
        when(authRequest.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(amazonUser.getName()).thenReturn("Grant");
        when(amazonUser.getEmail()).thenReturn("email@email.com");
        when(appAccountRepository.findByClientId(TEST_NONEXISTING_CLIENT_ID)).thenReturn(null);

    }

    @Test
    public void testValidAuthRequest() {
        when(appAccountRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(appAccount);
        assertTrue(classUnderTest.isValidAuthRequest(authRequest));

    }

    @Test
    public void testInvalidAuthRequest() {
        when(appAccountRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(null);
        assertFalse(classUnderTest.isValidAuthRequest(authRequest));
    }

    @Test
    public void testCreateAppCredential() {
        when(appAccountRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(appAccount);
        when(amazonLoginService.processLogin(amazonUser)).thenReturn(amazonUser);
        String authCode = classUnderTest.createAppCredential(amazonUser, authRequest);
        assertThat(authCode.length(), equalTo(36));
    }

    @Test
    public void testUnsuccessfulCreateAppCredential() {
        when(appAccountRepository.findByClientId(TEST_CLIENT_ID)).thenReturn(appAccount);
        when(amazonLoginService.processLogin(amazonUser)).thenReturn(null);
        String authCode = classUnderTest.createAppCredential(amazonUser, authRequest);
        assertThat(authCode, equalTo(null));
    }

    @Test
    public void testGetAccessTokenSuccess() {
        when(appAccountRepository.findByClientIdAndClientSecret(TEST_CLIENT_ID, TEST_CLIENT_SECRET)).thenReturn(
                appAccount);
        when(appCredentialRepository.findByAuthorizationCode(AUTH_CODE)).thenReturn(appCredential);
        when(appAccount.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(appCredential.getApp()).thenReturn(appAccount);
        when(appCredential.getAuthorizationCodeExpireTime()).thenReturn(LocalDateTime.now().plusMinutes(1));
        when(appCredential.getAccessTokenExpireTime()).thenReturn(LocalDateTime.now().plusMinutes(5));
        ResponseEntity response = classUnderTest.getAccessTokenFromAuthorizationCode(AUTH_CODE, TEST_CLIENT_ID, TEST_CLIENT_SECRET);
        assertThat(response, not(equalTo(null)));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(((ResponseEntity<AccessToken>) response).getBody().getClass(), equalTo(AccessToken.class));

    }
    
    @Test
    public void testGetAccessTokenInvalidClient() {
        when(appAccountRepository.findByClientIdAndClientSecret(TEST_CLIENT_ID, TEST_CLIENT_SECRET)).thenReturn(null);
        ResponseEntity response = classUnderTest.getAccessTokenFromAuthorizationCode(AUTH_CODE, TEST_CLIENT_ID, TEST_CLIENT_SECRET);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        AccessTokenError error = ((ResponseEntity<AccessTokenError>) response).getBody();
        assertThat(error.getClass(), equalTo(AccessTokenError.class));
        assertThat(error.getError(), equalTo(AccessTokenError.ERROR_TYPE.INVALID_CLIENT.getError()));
    }
    
    @Test
    public void testGetAccessTokenInvalidRequest() {
        when(appAccountRepository.findByClientIdAndClientSecret(TEST_CLIENT_ID, TEST_CLIENT_SECRET)).thenReturn(appAccount);
        when(appCredentialRepository.findByAuthorizationCode(AUTH_CODE)).thenReturn(null);
        ResponseEntity response = classUnderTest.getAccessTokenFromAuthorizationCode(AUTH_CODE, TEST_CLIENT_ID, TEST_CLIENT_SECRET);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        AccessTokenError error = ((ResponseEntity<AccessTokenError>) response).getBody();
        assertThat(error.getClass(), equalTo(AccessTokenError.class));
        assertThat(error.getError(), equalTo(AccessTokenError.ERROR_TYPE.INVALID_REQUEST.getError()));
    }
    
    @Mock
    AppAccount invalidAppAccount;
    
    @Test
    public void testGetAccessTokenInvalidRequestIncorrectClientId() {
        when(appAccountRepository.findByClientIdAndClientSecret(TEST_CLIENT_ID, TEST_CLIENT_SECRET)).thenReturn(appAccount);
        when(appCredentialRepository.findByAuthorizationCode(AUTH_CODE)).thenReturn(appCredential);
        when(appAccount.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(invalidAppAccount.getClientId()).thenReturn("invalid");
        when(appCredential.getApp()).thenReturn(invalidAppAccount);
        ResponseEntity response = classUnderTest.getAccessTokenFromAuthorizationCode(AUTH_CODE, TEST_CLIENT_ID, TEST_CLIENT_SECRET);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        AccessTokenError error = ((ResponseEntity<AccessTokenError>) response).getBody();
        assertThat(error.getClass(), equalTo(AccessTokenError.class));
        assertThat(error.getError(), equalTo(AccessTokenError.ERROR_TYPE.INVALID_REQUEST.getError()));
    }
    
    @Test
    public void testGetAccessTokenInvalidRequestAuthCodeExpired() {
        when(appAccountRepository.findByClientIdAndClientSecret(TEST_CLIENT_ID, TEST_CLIENT_SECRET)).thenReturn(appAccount);
        when(appCredentialRepository.findByAuthorizationCode(AUTH_CODE)).thenReturn(appCredential);
        when(appAccount.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(appCredential.getApp()).thenReturn(appAccount);
        when(appCredential.getAuthorizationCodeExpireTime()).thenReturn(LocalDateTime.now().minusMinutes(1));
        ResponseEntity response = classUnderTest.getAccessTokenFromAuthorizationCode(AUTH_CODE, TEST_CLIENT_ID, TEST_CLIENT_SECRET);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        AccessTokenError error = ((ResponseEntity<AccessTokenError>) response).getBody();
        assertThat(error.getClass(), equalTo(AccessTokenError.class));
        assertThat(error.getError(), equalTo(AccessTokenError.ERROR_TYPE.INVALID_GRANT.getError()));
    }

    @Test
    public void testRetrieveProfileSuccess() {
        when(appCredentialRepository.findByAccessToken(TEST_ACCESS_TOKEN)).thenReturn(appCredential);
        when(appCredential.getUser()).thenReturn(amazonUser);
        when(appCredential.getAppUserId()).thenReturn(TEST_APP_USER_ID);
        ResponseEntity response = classUnderTest.retrieveProfile(TEST_ACCESS_TOKEN);
        Profile profile = ((ResponseEntity<Profile>) response).getBody();
        assertThat(profile.getUser_id(), equalTo(TEST_APP_USER_ID));
    }
    
    @Test
    public void testRetrieveProfileFailure() {
        when(appCredentialRepository.findByAccessToken(TEST_ACCESS_TOKEN)).thenReturn(null);
        ResponseEntity response = classUnderTest.retrieveProfile(TEST_ACCESS_TOKEN);
        ProfileError profileError = ((ResponseEntity<ProfileError>) response).getBody();
        
        assertThat(profileError.getError(), equalTo(ProfileError.ERROR_TYPE.INVALID_ACCESS_TOKEN.getError()));
    }
    
    @Test
    public void testUseRefreshToken() {
        when(appCredentialRepository.findByRefreshToken(TEST_REFRESH_TOKEN)).thenReturn(appCredential);
        when(appCredential.getApp()).thenReturn(appAccount);
        ResponseEntity response = classUnderTest.getAccessTokenFromRefreshToken(TEST_REFRESH_TOKEN);
        AccessToken token = ((ResponseEntity<AccessToken>) response).getBody();
        assertThat(token, not(equalTo(null)));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        
    }
    
    @Test
    public void testUseRefreshTokenFailure() {
        when(appCredentialRepository.findByRefreshToken(TEST_REFRESH_TOKEN)).thenReturn(null);
        ResponseEntity response = classUnderTest.getAccessTokenFromRefreshToken(TEST_REFRESH_TOKEN);
        AccessTokenError tokenError = ((ResponseEntity<AccessTokenError>) response).getBody();
        assertThat(tokenError.getError(), equalTo(AccessTokenError.ERROR_TYPE.INVALID_REFRESH_TOKEN.getError()));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

}
