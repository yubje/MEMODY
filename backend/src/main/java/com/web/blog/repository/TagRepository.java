package com.web.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Tag;
import com.web.blog.domain.Users;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Optional<Tag> findByTname(String tname);
	
	Tag findTidByTname(String tname);	
}