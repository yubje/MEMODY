package com.web.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Post;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.MemberRepository;
import com.web.blog.repository.PostRepository;
import com.web.blog.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final BlogRepository blogRepository;
	private final MemberRepository memberRepository;
	private final UsersRepository userRepository;

	
	public void createPost(Post post) {
		System.out.println(post);
		postRepository.save(Post.builder()
				.bid(post.getBid())
				.lcid(post.getLcid())
				.mcid(post.getMcid())
				.ptitle(post.getPtitle())
				.pcontent(post.getPcontent())
				.author(post.getAuthor())
				.postTime(LocalDateTime.now())
				.update_time(LocalDateTime.now())
				.ptype(post.getPtype())
				.build()).getPid();
		// 게시글 작성 시 작성자 경험치 상승
		Optional<Users> user = userRepository.findByEmail(post.getAuthor());
		user.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+2);
			userRepository.save(selectUser);
		});
	}
	
//	public boolean countBlogByUser(String email) {
//		if(blogRepository.countByManager(email)>6) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//	
	public Page<Post> listAllPost(int bid, Pageable pageable){
//		List<Post> result = new ArrayList<Post>();
//		Page<Post> result = new ArrayList<Post>();
//		result = postRepository.findAllByBid(bid);
		// 최신글 순서로 조회 
//		result = postRepository.findAllByBidAndPtypeIsNullOrderByPostTimeDesc(bid);
		Page<Post> result = postRepository.findAllByBidAndPtypeIsNullOrderByPostTimeDesc(bid, pageable);
		return result;
	}

	public List<Post> listAllSavePost(int bid, String author){
		List<Post> result = new ArrayList<Post>();
		// 최신글 순서로 조회 
		result = postRepository.findAllByBidAndPtypeIsNotNullAndAuthorOrderByPostTimeDesc(bid, author);
		return result;
	}
	
	@Transactional
	public Post findByPid(int pid) {
		return postRepository.findByPid(pid);
	}
	
	public Post postInfo(int pid) {
		Post post = postRepository.findByPid(pid);
		// 게시글 조회 시 작성자의 경험치 상승
		Optional<Users> user = userRepository.findByEmail(post.getAuthor());
		user.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+1);
			userRepository.save(selectUser);
		});
		return post;
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

	public List<Post> listAllPostByMCategory(int bid, int mcid){
		List<Post> result = new ArrayList<Post>();
		// 최신글 순서로 조회 
		result = postRepository.findAllByBidAndMcidAndPtypeIsNullOrderByPostTimeDesc(bid, mcid);
		return result;
	}
	
	public boolean checkPost(int pid) {
		Post post = postRepository.findByPid(pid);
		if (post == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void forkPost(Post post) {
		// 내 블로그 목록 조회
		// 내 카테고리 조회
		// 선택한 후 lcid, mcid 랑 같이 
		Post post2 = postRepository.findByPid(post.getPid());
		postRepository.save(Post.builder()
				.bid(post.getBid())
				.lcid(post.getLcid())
				.mcid(post.getMcid())
				.ptitle(post2.getPtitle())
				.pcontent(post2.getPcontent())
				.author(post2.getAuthor())
				.postTime(LocalDateTime.now())
				.update_time(LocalDateTime.now())
				.ptype(post2.getPtype())
				.build());
		// 게시글 fork 시 원작자 경험치 상승
		Optional<Users> user = userRepository.findByEmail(post2.getAuthor());
		user.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+5);
			userRepository.save(selectUser);
		});
	}

}