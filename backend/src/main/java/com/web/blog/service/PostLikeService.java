package com.web.blog.service;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Post;
import com.web.blog.domain.PostLike;
import com.web.blog.repository.PostLikeRepository;
import com.web.blog.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;
	private final PostRepository postRepository;

	public void increasePostLike(int pid, String email) {
		postLikeRepository.save(PostLike.builder().pid(pid).email(email).build());
		Post updatePost = postRepository.findByPid(pid);
		updatePost.setPostlikecnt(postLikeRepository.countByPid(pid));
		postRepository.save(updatePost);
	}
	
	public void decreasePostLike(int pid, String email) {
		postLikeRepository.deleteByEmailAndPid(email, pid);
		Post updatePost = postRepository.findByPid(pid);
		updatePost.setPostlikecnt(postLikeRepository.countByPid(pid));
		postRepository.save(updatePost);
	}

	
}