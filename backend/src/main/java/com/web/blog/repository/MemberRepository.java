package com.web.blog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	List<Member> findByEmail(String email);
	List<Member> findByBid(int bid);
	
	@Transactional
	void deleteByEmailAndBid(String email, int bid);
	
	int countByBid(int bid);
	
	

}