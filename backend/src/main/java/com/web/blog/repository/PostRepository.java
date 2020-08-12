package com.web.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

//	long countByManager(String manager);
	
	Post findByPid(int pid);
	
	List<Post> findAllByBid(int bid);
//	List<Post> findAllByBid(int bid, Pageable pageable);
	
	void deleteByPid(int pid);
	
//	List<Post> findAllByBidOrderByPostTimeDesc(int bid);
	
	// 작성 글 조회 최신순으로 
//	List<Post> findAllByBidAndPtypeIsNullOrderByPostTimeDesc(int bid);
	Page<Post> findAllByBidAndPtypeIsNullOrderByPostTimeDesc(int bid, Pageable pageable);
	
	// 임시저장 글 조회
	List<Post> findAllByBidAndPtypeIsNotNullAndAuthorOrderByPostTimeDesc(int bid, String author);

	// 카테고리 내 전체 게시글 조회 (임시저장글 제외)
//	List<Post> findAllByBidAndMcidAndPtypeIsNullOrderByPostTimeDesc(int bid, int mcid);
	Page<Post> findAllByBidAndMcidAndPtypeIsNullOrderByPostTimeDesc(int bid, int mcid, Pageable pageable);
}