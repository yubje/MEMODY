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
		System.out.println(blog);
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
		System.out.println("blogService >> "+len);
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
}