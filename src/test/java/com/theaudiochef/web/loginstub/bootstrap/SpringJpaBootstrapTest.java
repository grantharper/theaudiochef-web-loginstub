package com.theaudiochef.web.loginstub.bootstrap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.theaudiochef.web.loginstub.WebApplication;
import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class SpringJpaBootstrapTest {

	@Autowired
	AmazonUserRepository userRepository;
	
	@Test
	public void userLoads() {
		List<AmazonUser> users = userRepository.findAll();
		assertThat(users.size(), is(equalTo(1)));
	}


}
