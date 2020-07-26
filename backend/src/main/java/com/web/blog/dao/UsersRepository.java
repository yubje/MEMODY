package com.web.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}