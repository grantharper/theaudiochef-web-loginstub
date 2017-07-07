package com.theaudiochef.web.loginstub.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class AppCredentialTest {
    
    private AppCredential appCred;
    
    @Before
    public void setup(){
        appCred = new AppCredential();
    }

    @Test
    public void testGenerateRandomUUID() {
        String UUID = appCred.generateRandomUUID();
        assertThat(UUID, not(equalTo(null)));
        assertThat(UUID.length(), equalTo(36));
    }
    
    @Test
    public void testConstructor() {
        AppAccount account = mock(AppAccount.class);
        AmazonUser user = mock(AmazonUser.class);
        
        appCred = new AppCredential(user, account);
        
        assertThat(appCred.getAccessToken().length(), equalTo(36));
        assertThat(appCred.getAmazonTokenType(), equalTo("code"));
    }

}
