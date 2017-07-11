package com.theaudiochef.web.loginstub.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AppCredentialTest {
    
    @InjectMocks
    private AppCredential classUnderTest;
    
    @Mock
    AppAccount account;
    
    @Mock
    AmazonUser user;
    
    @Before
    public void setup(){
        classUnderTest = new AppCredential(user, account, 300000);
        MockitoAnnotations.initMocks(this);
        
    }

    @Test
    public void testGenerateRandomUUID() {
        String UUID = classUnderTest.generateRandomUUID();
        assertThat(UUID, not(equalTo(null)));
        assertThat(UUID.length(), equalTo(36));
    }
    
    @Test
    public void testStandardAppCredentialCreationConstructor() {
        
        assertThat(classUnderTest.getAccessToken().length(), equalTo(36));
        assertThat(classUnderTest.getAmazonTokenType(), equalTo("code"));
        assertThat(classUnderTest.getAuthorizationCode().length(), equalTo(36));
        assertThat(classUnderTest.getRefreshToken().length(), equalTo(36));
        assertThat(classUnderTest.getAppUserId().length(), equalTo(36));
       
    }
    
    @Test
    public void testRegenerateAccessToken() {
        String previousAccessToken = classUnderTest.getAccessToken();
        LocalDateTime previousAccessTokenExpireTime = classUnderTest.getAccessTokenExpireTime();
        
        classUnderTest.resetAccessToken(LocalDateTime.now().plusMinutes(6));
        String nextAccessToken = classUnderTest.getAccessToken();
        LocalDateTime nextAccessTokenExpireTime = classUnderTest.getAccessTokenExpireTime();
        assertThat(nextAccessToken, not(equalTo(previousAccessToken)));
        assertThat(nextAccessTokenExpireTime, not(equalTo(previousAccessTokenExpireTime)));
        
    }

}
