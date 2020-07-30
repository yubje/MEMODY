package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.LCategory;
import com.web.blog.domain.Users;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface LCategoryRepository extends JpaRepository<LCategory, Long> {

	LCategory findByLcid(int lcid);

	@Transactional
	void deleteByLcid(int lcid);
	
	List<LCategory> findByBid(int bid);
}