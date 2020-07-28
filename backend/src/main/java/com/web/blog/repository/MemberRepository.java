package com.web.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	List<Member> findByEmail(String email);

}