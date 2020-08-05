package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	long countByManager(String manager);
	
	Blog findByBid(int bid);
	
	@Transactional
	void deleteByBid(int bid);
	
	List<Blog> findDistinctByBtitleContaining(String bname);
	
}