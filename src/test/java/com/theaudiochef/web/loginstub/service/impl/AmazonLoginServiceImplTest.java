package com.theaudiochef.web.loginstub.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;

public class AmazonLoginServiceImplTest {

    private static final String TEST_USERNAME = "user1";
    private static final String TEST_PASSWORD = "pw";

    @InjectMocks
    private AmazonLoginServiceImpl classUnderTest;

    @Mock
    private AmazonUserRepository userRepository;

    @Mock
    private AmazonUser loginCredential;

    @Spy
    private AmazonUser existingUser;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(loginCredential.getUsername()).thenReturn(TEST_USERNAME);
        when(loginCredential.getPassword()).thenReturn(TEST_PASSWORD);
        doReturn(TEST_USERNAME).when(existingUser).getUsername();
        doReturn(TEST_PASSWORD).when(existingUser).getPassword();
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(existingUser);
    }

    @Test
    public void testSuccessProcessLogin() {

        assertThat(classUnderTest.processLogin(loginCredential), equalTo(existingUser));
    }

    @Test
    public void testUserDoesNotExistProcessLogin() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(null);

        assertThat(classUnderTest.processLogin(loginCredential), equalTo(null));
    }

    @Test
    public void testPasswordIncorrect() {
        doReturn("different").when(existingUser).getPassword();
        assertThat(classUnderTest.processLogin(loginCredential), equalTo(null));
    }

}
