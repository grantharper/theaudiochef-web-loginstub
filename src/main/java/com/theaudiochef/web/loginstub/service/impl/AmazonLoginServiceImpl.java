package com.theaudiochef.web.loginstub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.repository.AmazonUserRepository;
import com.theaudiochef.web.loginstub.service.AmazonLoginService;

@Service
public class AmazonLoginServiceImpl implements AmazonLoginService {

    private AmazonUserRepository amazonUserRepository;
    
    @Autowired
    public AmazonLoginServiceImpl(AmazonUserRepository amazonUserRepository){
        this.amazonUserRepository = amazonUserRepository;
    }

    @Override
    public AmazonUser processLogin(AmazonUser loginCredentials) {
        AmazonUser targetedUser = amazonUserRepository.findByUsername(loginCredentials.getUsername());

        if (targetedUser != null && targetedUser.verifyPassword(loginCredentials)) {
            return targetedUser;
        }

        return null;
    }

}
