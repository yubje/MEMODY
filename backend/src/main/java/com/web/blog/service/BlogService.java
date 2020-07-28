package com.web.blog.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Member;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.MemberRepository;
import com.web.blog.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class BlogService {

	private final BlogRepository blogRepository;
	private final MemberRepository memberRepository;
	
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
			result.add(blogRepository.findByBid(member.getBid()));
		}
		return result;
	}

}