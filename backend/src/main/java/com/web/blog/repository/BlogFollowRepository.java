package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.BlogFollow;
import com.web.blog.domain.Users;

public interface BlogFollowRepository extends JpaRepository<BlogFollow, Long> {
	
	List<Users> findByEmail(String email);
	List<Users> findByBid(int bid);
	
	@Transactional
	void deleteByEmailAndBid(String email, int bid);
	
	int countByBid(int bid);
	
	

}