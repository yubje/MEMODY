package com.web.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Post;
import com.web.blog.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
//	private final MemberRepository memberRepository;
	
	public int createPost(Post Post) {
		System.out.println(Post);
		return postRepository.save(Post.builder()
				.bid(Post.getBid())
				.lcid(Post.getLcid())
				.mcid(Post.getMcid())
				.ptitle(Post.getPtitle())
				.pcontent(Post.getPcontent())
				.author(Post.getAuthor())
				.postTime(LocalDateTime.now())
				.update_time(LocalDateTime.now())
				.ptype(Post.getPtype())
				.build()).getPid();
	}
	
//	public boolean countBlogByUser(String email) {
//		if(blogRepository.countByManager(email)>6) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//	
	public List<Post> listAllPost(int bid){
		List<Post> result = new ArrayList<Post>();
//		result = postRepository.findAllByBid(bid);
		// 최신글 순서로 조회 
		result = postRepository.findAllByBidAndPtypeIsNullOrderByPostTimeDesc(bid);
		return result;
	}
	
	@Transactional
	public Post findByPid(int pid) {
		return postRepository.findByPid(pid);
	}
	
	public void updatePost(Post post) {
		Post updatePost = postRepository.findByPid(post.getPid());
		updatePost.setPtitle(post.getPtitle());
		updatePost.setPcontent(post.getPcontent());
		updatePost.setUpdate_time(LocalDateTime.now());
		updatePost.setPtype(post.getPtype());
		postRepository.save(updatePost);
	}
	
	@Transactional
	public void deletePost(int pid) {
		postRepository.deleteByPid(pid);
	}

}