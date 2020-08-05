package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Member;
import com.web.blog.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	
	List<Member> findByEmail(String email);
	List<Member> findByPid(int pid);
	
	@Transactional
	void deleteByEmailAndPid(String email, int pid);
	
	int countByPid(int pid);
	
	

}