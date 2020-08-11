package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Blogtag;
import com.web.blog.domain.Fork;
import com.web.blog.domain.Users;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface ForkRepository extends JpaRepository<Fork, Integer> {
	List<Fork> findByPid(int pid);
}