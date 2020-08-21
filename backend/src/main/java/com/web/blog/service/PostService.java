package com.web.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Fork;
import com.web.blog.domain.Post;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.ForkRepository;
import com.web.blog.repository.MemberRepository;
import com.web.blog.repository.PostRepository;
import com.web.blog.repository.UsersRepository;
import com.web.blog.util.S3Util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final BlogRepository blogRepository;
	private final ForkRepository forkRepository;
	private final MemberRepository memberRepository;
	private final UsersRepository userRepository;

	private final String ADMIN = "ROLE_ADMIN";
	
	public void createPost(Post post) {
		postRepository.save(Post.builder()
				.bid(post.getBid())
				.lcid(post.getLcid())
				.mcid(post.getMcid())
				.ptitle(post.getPtitle())
				.pcontent(post.getPcontent())
				.author(post.getAuthor())
				.manager(post.getManager())
				.postTime(LocalDateTime.now())
				.updateTime(LocalDateTime.now())
				.ptype(post.getPtype())
				.build()).getPid();
		// 게시글 작성 시 작성자 경험치 상승
		Optional<Users> user = userRepository.findByEmail(post.getAuthor());
		user.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+2);
			userRepository.save(selectUser);
		});
	}
	
	public Page<Post> listAllPost(int bid, Pageable pageable){
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
	
	public void updatePost(String content, Post post,String bucketName, String accessKey, String secretKey) {
		Post updatePost = postRepository.findByPid(post.getPid());
		
		System.out.println("[이전 데이터] "+content);
		System.out.println("[바뀔 데이터] "+post.getPcontent());
		
		if(content.contains("img") && !content.equals(post.getPcontent())) {
			String[] inputArr = content.split("'");
			for(int i=0;i<inputArr.length;i++) {
				if(inputArr[i].contains("https://memody")) {
					String[] temp = inputArr[i].split("/");
					S3Util s3 = new S3Util(accessKey, secretKey);
					String temp2 = temp[3]+"/"+temp[4]+"/"+temp[5]+"/"+temp[6]+"/"+temp[7];
					s3.fileDelete(bucketName, temp2);
				}
			}
		}
		
		updatePost.setPtitle(post.getPtitle());
		updatePost.setPcontent(post.getPcontent());
		updatePost.setUpdate_time(LocalDateTime.now());
		updatePost.setPtype(post.getPtype());
		postRepository.save(updatePost);
	}
	
	@Transactional
	public boolean deletePost(String user, int pid, String role) {
		Post post = postRepository.findByPid(pid);
		if(user.equals(post.getManager()) | role.equals(ADMIN)) {
			postRepository.deleteByPid(pid);
			return true;
		}else {
			return false;
		}
	}

	public Page<Post> listAllPostByMCategory(int bid, int mcid, Pageable pageable){
		// 최신글 순서로 조회 
		Page<Post> result = postRepository.findAllByBidAndMcidAndPtypeIsNullOrderByPostTimeDesc(bid, mcid, pageable);
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
	
	// 해당 pid에 대한 fork 한 사용자 목록
	public List<Fork> forkList(int pid){
		return forkRepository.findByPid(pid);
	}
	
	public void forkPost(Post post,String user,String uid) {
		// 내 블로그 목록 조회
		// 내 카테고리 조회
		// 선택한 후 lcid, mcid 랑 같이 
		Post post2 = postRepository.findByPid(post.getPid());
		post2.setFork(post2.getFork()+1);
		postRepository.save(post2);
		
		// fork한 유저 목록 
		forkRepository.save(Fork.builder()
				.pid(post2.getPid())
				.email(user)
				.uid(uid)
				.build());
		
		postRepository.save(Post.builder()
				.bid(post.getBid())
				.lcid(post.getLcid())
				.mcid(post.getMcid())
				.ptitle(post2.getPtitle())
				.pcontent(post2.getPcontent())
				.author(post2.getAuthor())
				.manager(user)
				.postTime(LocalDateTime.now())
				.updateTime(LocalDateTime.now())
				.ptype(post2.getPtype())
				.build());

		// 게시글 fork 시 원작자 경험치 상승
		Optional<Users> author = userRepository.findByEmail(post2.getAuthor());
		author.ifPresent(selectUser->{
			selectUser.setExp(selectUser.getExp()+5);
			userRepository.save(selectUser);
		});
	}

}