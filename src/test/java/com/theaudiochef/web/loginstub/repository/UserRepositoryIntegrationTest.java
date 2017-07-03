package com.theaudiochef.web.loginstub.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {
	
	@Autowired
	AmazonUserRepository userRepository;
	
	@Test
	public void testInsert() {
		AmazonUser user = new AmazonUser();
		user.setName("John");
		userRepository.save(user);
		assertThat(user.getId(), is(equalTo(1L)));
	}

}
