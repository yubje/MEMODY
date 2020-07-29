package com.web.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.web.blog.domain.Post;
import com.web.blog.domain.Users;
import com.web.blog.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
//	private final MemberRepository memberRepository;
	
	public int createPost(Post post) {
		System.out.println(post);
		return postRepository.save(Post.builder()
				.bid(post.getBid())
				.lcid(post.getLcid())
				.mcid(post.getMcid())
				.ptitle(post.getPtitle())
				.pcontent(post.getPcontent())
				.author(post.getAuthor())
				.post_time(LocalDateTime.now())
				.update_time(LocalDateTime.now())
				.author(post.getAuthor())
				.ptype(post.getPtype())
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
	public List<Post> postListAll(int bid){
		List<Post> result = new ArrayList<Post>();
		result = postRepository.findByBid(bid);
		return result;
	}
	
	@Transactional
	public Post findByPid(int pid) {
		return postRepository.findByPid(pid);
	}

}