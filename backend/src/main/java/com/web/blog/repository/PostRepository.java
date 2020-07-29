package com.web.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

//	long countByManager(String manager);
	
	Post findByPid(int pid);
	
	List<Post> findByBid(int bid);
	
	void deleteByPid(int pid);
}