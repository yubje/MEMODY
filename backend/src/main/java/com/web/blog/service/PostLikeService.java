package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Post;
import com.web.blog.domain.PostLike;
import com.web.blog.domain.Users;
import com.web.blog.repository.PostLikeRepository;
import com.web.blog.repository.PostRepository;
import com.web.blog.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;
	private final PostRepository postRepository;
	private final UsersRepository userRepository;

	public void increasePostLike(int pid, String email) {
		postLikeRepository.save(PostLike.builder().pid(pid).email(email).build());
		Post updatePost = postRepository.findByPid(pid);
		updatePost.setPostlikecnt(postLikeRepository.countByPid(pid));
		postRepository.save(updatePost);
		// 게시글 좋아요 시 작성자의 경험치 상승 
		Optional<Users> user = userRepository.findByEmail(updatePost.getAuthor());
		user.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+3);
			userRepository.save(selectUser);
		});
	}
	
	public void decreasePostLike(int pid, String email) {
		postLikeRepository.deleteByEmailAndPid(email, pid);
		Post updatePost = postRepository.findByPid(pid);
		updatePost.setPostlikecnt(postLikeRepository.countByPid(pid));
		postRepository.save(updatePost);
	}
	
	public boolean searchPostLike(int pid, String email) {
		List<PostLike> list = postLikeRepository.findByPidAndEmail(pid, email);
		if(list.size()==1) {
			return true;
		}else {
			return false;
		}
	}
	
}