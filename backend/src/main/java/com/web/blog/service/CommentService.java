package com.web.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Comments;
import com.web.blog.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	public void createComments(Comments comment) {
		System.out.println(comment);
		commentRepository.save(Comments.builder()
				.pid(comment.getPid())
				.comment(comment.getComment())
				.email(comment.getEmail())
				.comment_time(LocalDateTime.now())
				.update_time(LocalDateTime.now())
				.build());
	}
	
	public List<Comments> listAllComments(int pid){
		List<Comments> result = new ArrayList<Comments>();
		result = commentRepository.findByPid(pid);
		return result;
	}
	
	@Transactional
	public Comments findByCmid(int cmid) {
		return commentRepository.findByCmid(cmid);
	}
	
	public void updateComments(Comments comment) {
		Comments updateComments = commentRepository.findByCmid(comment.getCmid());
		updateComments.setComment(comment.getComment());
		updateComments.setUpdate_time(LocalDateTime.now());
		commentRepository.save(updateComments);
	}
	
	@Transactional
	public void deleteComments(int cmid) {
		commentRepository.deleteByCmid(cmid);
	}

}