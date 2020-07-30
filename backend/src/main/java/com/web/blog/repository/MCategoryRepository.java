package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.LCategory;
import com.web.blog.domain.MCategory;
import com.web.blog.domain.Users;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface MCategoryRepository extends JpaRepository<MCategory, Long> {

	@Transactional
	void deleteByMcid(int mcid);
	
	List<MCategory> findByLcid(int lcid);
}