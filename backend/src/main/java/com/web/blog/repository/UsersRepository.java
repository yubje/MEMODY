package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Users;
import com.web.blog.domain.Users.UsersBuilder;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    
    void deleteByEmail(String email);
    
    Optional<Users> findByUid(String uid);

}