package com.web.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    
    void deleteByEmail(String email);
    
    Optional<Users> findByUid(String uid);

//    List<Users> findAllByOrderByExpDesc();
    List<Users> findTop10ByOrderByExpDesc();

    List<Users> findByUidContaining(String uid);

    List<Users> findAllByOrderByEmail();
}