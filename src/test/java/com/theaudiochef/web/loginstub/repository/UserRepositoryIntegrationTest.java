package com.theaudiochef.web.loginstub.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AmazonUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {
	
	@Autowired
	AmazonUserRepository userRepository;
	
	//NOTE: These tests are dependent on the JpaBootstrapping class to load the data
	
	@Test
	public void testInsert() {
		AmazonUser user = userRepository.findByUsername("gharper");
		assertThat(user.getPassword(), equalTo("password"));
	}

}
