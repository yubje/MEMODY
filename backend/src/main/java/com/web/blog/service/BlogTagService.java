package com.web.blog.service;

import lombok.RequiredArgsConstructor;

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
import com.web.blog.domain.Blogtag;
import com.web.blog.domain.Users;
import com.web.blog.repository.BlogRepository;
import com.web.blog.repository.BlogTagRepository;
import com.web.blog.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class BlogTagService {

	private final BlogTagRepository blogtagRepository;

	public void blogAndTag(int bid, int tid, String tname) {
		blogtagRepository.save(Blogtag.builder().bid(bid).tid(tid).tname(tname).build());
	}
	
	public void deleteByBid(int bid) {
		blogtagRepository.deleteByBid(bid);	
	}
	
	public void updateTag(int bid,int tid,String tname) {
		blogtagRepository.save(Blogtag.builder().bid(bid).tid(tid).tname(tname).build());
	}

}