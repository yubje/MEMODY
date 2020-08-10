package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.BlogFollow;
import com.web.blog.domain.PostLike;
import com.web.blog.domain.Users;

public interface BlogFollowRepository extends JpaRepository<BlogFollow, Long> {
	
	List<BlogFollow> findByEmail(String email);
	List<BlogFollow> findByBid(int bid);
	
	@Transactional
	void deleteByEmailAndBid(String email, int bid);
	
	int countByBid(int bid);
	
	List<BlogFollow> findByBidAndEmail(int bid, String email);

}