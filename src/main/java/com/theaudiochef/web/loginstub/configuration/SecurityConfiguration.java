package com.theaudiochef.web.loginstub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
        httpSecurity.authorizeRequests().antMatchers("/**", "/console/**").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
		
	}
	
}
