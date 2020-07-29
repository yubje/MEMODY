package com.web.blog.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Blogtag;
import com.web.blog.domain.Member;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.BlogTagRepository;
import com.web.blog.repository.MemberRepository;
import com.web.blog.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class BlogService {

	private final BlogRepository blogRepository;
	private final MemberRepository memberRepository;
	private final BlogTagRepository blogtagRepository;
	
	public int createBlog(Blog blog) {
		return blogRepository.save(Blog.builder()
				.btitle(blog.getBtitle())
				.bsubtitle(blog.getBsubtitle())
				.bcontent(blog.getBcontent())
				.manager(blog.getManager()).build()).getBid();
	}
	
	public boolean countBlogByUser(String email) {
		if(blogRepository.countByManager(email)>6) {
			return false;
		}else {
			return true;
		}
	}
	
	public List<Blog> myBlogList(String email){
		List<Member> list = memberRepository.findByEmail(email);
		List<Blog> result = new ArrayList<Blog>();
		for(Member member:list) {
			int bid = member.getBid();
			Blog blog = blogRepository.findByBid(bid);
			
			List<Blogtag> hashtags = blogtagRepository.findByBid(bid);
			for(Blogtag tag:hashtags) {
				blog.addHashTag(tag);
			}
			result.add(blog);
		}
		return result;
	}

	public boolean checkBlog(int bid) {
		Blog blog = blogRepository.findByBid(bid);
		if(blog==null) {
			return false;
		}else {
			return true;
		}
	}
	
	public Blog blogInfo(int bid) {
		Blog blog = blogRepository.findByBid(bid);
		blog.setViews(blog.getViews()+1);
		blogRepository.save(blog);
		
		List<Blogtag> hashtags = blogtagRepository.findByBid(bid);
		for(Blogtag tag:hashtags) {
			blog.addHashTag(tag);
		}
		return blog;
	}
	
	public List<Blog> recommendBlogs(){
		List<Blog> list = blogRepository.findAll();
		Collections.sort(list,new Comparator<Blog>() {
			@Override
			public int compare(Blog o1, Blog o2) {
				return o2.getViews()-o1.getViews();
			}
		});
		int len = list.size();
		for(int i=0;i<len;i++) {
			List<Blogtag> hashtags = blogtagRepository.findByBid(list.get(i).getBid());
			for(Blogtag tag:hashtags) {
				list.get(i).addHashTag(tag);
			}
		}
		
		if(len>6) {
			return list.subList(0, 6);
		}else {
			return list;
		}
	}
	
	//////////////////////////////
	
	// 수정
	//blogService.updateBlog(user, changeBlog,changeTag,bid))
	public boolean updateBlog(String user,Blog changeBlog,String changeTag, int bid) {
		Blog blog = blogRepository.findByBid(bid);
		System.out.println(blog);
		if(user.equals(blog.getManager())) {
			System.out.println("수정 시작");
			blogRepository.save(Blog.builder()
					.bid(bid)
					.btitle(changeBlog.getBtitle())
					.bsubtitle(changeBlog.getBsubtitle())
					.bcontent(changeBlog.getBcontent())
					.manager(blog.getManager())
					.views(blog.getViews())
					.build());
			
			return true;
		}else {
			return false;
		}
	}
	
	// 삭제
	public boolean deleteBlog(String user,int bid) {
		Blog blog = blogRepository.findByBid(bid);
		if(user.equals(blog.getManager())) {
			System.out.println("수정 시작");
			blogRepository.deleteByBid(bid);
			return true;
		}else {
			return false;
		}
		
	}
	
	
}