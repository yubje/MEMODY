package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Users;

import java.util.Optional;

import javax.transaction.Transactional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

	long countByManager(String manager);
	
	Blog findByBid(int bid);
	
	@Transactional
	void deleteByBid(int bid);
	
}