package com.web.blog.service;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.blog.domain.Blog;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class BlogService {

	private final BlogRepository blogRepository;

//	public void createBlog(Blog blog) {
//		System.out.println(blog);
//		blogRepository.save(Blog.builder()
//				.bTitle(blog.getBTitle())
//				.bSubTitle(blog.getBSubTitle())
//				.bContent(blog.getBContent())
//				.manager(blog.getManager()).build());
//	}
	
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

}