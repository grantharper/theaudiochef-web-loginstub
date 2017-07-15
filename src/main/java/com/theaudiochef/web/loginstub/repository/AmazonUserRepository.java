package com.theaudiochef.web.loginstub.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theaudiochef.web.loginstub.domain.AmazonUser;


@Repository
@Transactional
public interface AmazonUserRepository extends JpaRepository<AmazonUser, Long>{

    AmazonUser findByUsername(String username);
    
}
