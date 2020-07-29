package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Blogtag;
import com.web.blog.domain.Users;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface BlogTagRepository extends JpaRepository<Blogtag, Long> {
	List<Blogtag> findByBid(int bid);
	@Transactional
	void deleteByBid(int bid);
	
	List<Blogtag> findDistinctByTnameContaining(String tname);
}