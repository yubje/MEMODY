package com.web.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

//	long countByManager(String manager);
	
	Post findByPid(int pid);
	
	List<Post> findAllByBid(int bid);
	
	void deleteByPid(int pid);
	
//	List<Post> findAllByBidOrderByPostTimeDesc(int bid);
	
	// 작성 글 조회 최신순으로 
	List<Post> findAllByBidAndPtypeIsNullOrderByPostTimeDesc(int bid);
	
	// 임시저장 글 조회
	List<Post> findAllByBidAndPtypeIsNotNullAndAuthorOrderByPostTimeDesc(int bid, String author);
	
}