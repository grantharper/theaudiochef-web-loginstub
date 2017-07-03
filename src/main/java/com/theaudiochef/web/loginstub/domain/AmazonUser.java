package com.theaudiochef.web.loginstub.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class AmazonUser extends AbstractDomainClass {

	private String name;

	private String email;
	
	private String username;
	
	private String password;

	

	public AmazonUser() {
	}
	
	public boolean verifyPassword(AmazonUser loginCredentials){
	    if(this.password.equals(loginCredentials.getPassword())){
	        return true;
	    }
	    return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	
	
	
}
