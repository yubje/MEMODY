package com.web.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.domain.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {

	Comments findByCmid(int cmid);
	
	List<Comments> findAllByPidOrderByCommentTimeDesc(int pid);
	
	void deleteByCmid(int cmid);
}
